# Intro

We first declare a queue called `rs_customer_<tenant>` for each tenant (a, b, c, d). 
This queue is just used to control (semaphore) access to the tenant, and exactly one message is published to each `rs_customer_<tenant>` queue.

The first thread/process to arrive will get the message and all the others will sit idle waiting for it (or try to get messages from other tenants). 

The trick will be that these processes will never acknowledge the message, but they will consume from the `rs_customer_<tenant>` queue , do the job* (retrieved from other queue `rs_customer_<tenant>`) and *basicReject*. So, RabbitMQ will keep track of the message and if the processes crashes or exists, the message will go back to the queue, and it will be delivered to the next process listening from our semaphore queue.

`(*)` The real job will consume tasks and remove from the queue (normal consumer workflow)

https://blog.rabbitmq.com/posts/2014/02/distributed-semaphores-with-rabbitmq/

### Test

```
curl -X POST http://localhost:8080/config
curl -X POST http://localhost:8080/produce
curl -X POST http://localhost:8080/consume?threads=5
```