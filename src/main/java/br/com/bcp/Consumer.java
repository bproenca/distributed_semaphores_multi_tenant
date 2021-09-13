package br.com.bcp;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.Channel;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Service.class);

    @Autowired
    private ConnectionFactory factory;

    @Async
    public void lockAndConsume(String resource) throws InterruptedException, IOException, TimeoutException {
        logger.info("Trying lock {}", resource);
        Connection connection = factory.createConnection();
        Channel channel = connection.createChannel(false);

        channel.queueDeclare(resource, true, false, false, null);
        GetResponse response = channel.basicGet(resource, false);
        if (response != null) {
            String queue = new String(response.getBody(), "UTF-8");
            logger.info("Locked resource {}", resource);
            consume(queue);
            channel.basicReject(response.getEnvelope().getDeliveryTag(), true);
        } else {
            logger.info("NOT Locked resource {}", resource);
        }
        
        channel.close();
        connection.close();
        //sleep(1000);
    }

    private void consume(String resource) throws InterruptedException, IOException, TimeoutException {
        Connection connection = factory.createConnection();
        Channel channel = connection.createChannel(false);

        channel.queueDeclare(resource, true, false, false, null);
        GetResponse response = channel.basicGet(resource, false);
        if (response != null) {
            String message = new String(response.getBody(), "UTF-8");
            logger.info("Consumed from queue: {} message: {}", resource, message);
            sleep(1000);
            channel.basicAck(response.getEnvelope().getDeliveryTag(), false);
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
