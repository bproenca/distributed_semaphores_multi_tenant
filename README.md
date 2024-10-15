# Intro

We first declare a queue called `resource_lock_tenant_<tenant>` for each tenant (a, b, c, d). 
This queue is just used to control (semaphore) access to the tenant, and exactly one* message is published to each `resource_lock_tenant_<tenant>` queue.

_PS*: you can add more messages if you want to implement **backpressure** instead of **fifo** (in this example we have diff number of msgs per tenant)._

The first thread/process to arrive will get the message and all the others will sit idle waiting for it (or try to get messages from other tenants). 

The trick will be that these processes will never acknowledge the message, but they will consume from the `resource_lock_tenant_<tenant>` queue , do the job* (retrieved from other queue `resource_lock_tenant_<tenant>`) and *basicReject*. So, RabbitMQ will keep track of the message and if the processes crashes or exists, the message will go back to the queue, and it will be delivered to the next process listening from our semaphore queue.

`(*)` The real job will consume tasks and remove from the queue (normal consumer workflow)

https://blog.rabbitmq.com/posts/2014/02/distributed-semaphores-with-rabbitmq/

### Start RabbitMQ

```sh
docker run -d --hostname bcp-my-rabbit --name bcp-some-rabbit \
 -p 5672:5672 -p 15672:15672 \
 -v /home/bcp/wks/volumes/rabbitmq:/var/lib/rabbitmq \
 rabbitmq:3-management
```

### Test

```sh
./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"

curl -X POST http://localhost:8080/config
curl -X POST http://localhost:8080/produce
curl -X POST "http://localhost:8080/consume?threads=2&iterations=20"
```

*or use th `.sh` scripts*