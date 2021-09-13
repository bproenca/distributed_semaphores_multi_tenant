package br.com.bcp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void produce(String queue, String message) {
        rabbitTemplate.convertAndSend(queue, message);        
    }
}
