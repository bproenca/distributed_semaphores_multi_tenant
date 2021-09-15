package br.com.bcp;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Service.class);

    @Autowired
    private ConnectionFactory factory;


    @Async
    public void startConsume(Integer iterations) {
        logger.info("This thread will iterate {} times, each iteration consumes 1 message of each tenant", iterations);
        for (int i = 0; i < iterations; i++) {
            for (Tenant t : Tenant.values()) {
                try {
                    logger.info("Trying lock resource {} (iteration {})", t.getResource().toUpperCase(), i);
                    lockAndConsume(t.getResource());
                } catch (InterruptedException | IOException | TimeoutException e) {
                    logger.error("Something went wrong", e);
                }
            }
            //sleep(1000);
        }
    }

    public void lockAndConsume(String resource) throws InterruptedException, IOException, TimeoutException {
        Connection connection = factory.createConnection();
        Channel channel = connection.createChannel(false);

        channel.queueDeclare(resource, true, false, false, null);
        GetResponse response = channel.basicGet(resource, false);
        if (response != null) {
            String queue = new String(response.getBody(), "UTF-8");
            logger.info("Locked resource {}", resource.toUpperCase());
            consume(queue);
            channel.basicReject(response.getEnvelope().getDeliveryTag(), true);
        } else {
            logger.info("Resource {} already locked", resource.toUpperCase());
        }
        
        channel.close();
        connection.close();
    }

    private void consume(String queue) throws InterruptedException, IOException, TimeoutException {
        Connection connection = factory.createConnection();
        Channel channel = connection.createChannel(false);

        channel.queueDeclare(queue, true, false, false, null);
        GetResponse response = channel.basicGet(queue, false);
        if (response != null) {
            String message = new String(response.getBody(), "UTF-8");
            int sleepTime = new Random().nextInt(9000) + 1;
            logger.info("Consumed from queue: {} message: {} ({} seconds)", queue.toUpperCase(), message, sleepTime);
            sleep(sleepTime);
            channel.basicAck(response.getEnvelope().getDeliveryTag(), false);
        } else {
            logger.info("No job to be done for queue {}", queue.toUpperCase());
        }
        
        channel.close();
        connection.close();
    }

    private void sleep(long time) {
        
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            logger.error("Sleep exception", e);
        }
        
    }
}
