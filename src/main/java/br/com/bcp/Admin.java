package br.com.bcp;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Admin {

    @Autowired 
    private RabbitAdmin rbAdmin;

    public void purge(String queue) {
        rbAdmin.purgeQueue(queue, false);
    }
}
