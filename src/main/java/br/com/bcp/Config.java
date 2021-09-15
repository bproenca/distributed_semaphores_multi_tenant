package br.com.bcp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
            new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rbAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue customer_a() {
       return new Queue("customer_a");
    }

    @Bean
    public Queue customer_b() {
       return new Queue("customer_b");
    }

    @Bean
    public Queue customer_c() {
       return new Queue("customer_c");
    }

    @Bean
    public Queue customer_d() {
       return new Queue("customer_d");
    }

    @Bean
    public Queue rs_customer_a() {
       return new Queue("rs_customer_a");
    }

    @Bean
    public Queue rs_customer_b() {
       return new Queue("rs_customer_b");
    }

    @Bean
    public Queue rs_customer_c() {
       return new Queue("rs_customer_c");
    }

    @Bean
    public Queue rs_customer_d() {
       return new Queue("rs_customer_d");
    }
}
