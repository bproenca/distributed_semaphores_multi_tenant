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
    public Queue tenant_a() {
       return new Queue("tenant_a");
    }

    @Bean
    public Queue tenant_b() {
       return new Queue("tenant_b");
    }

    @Bean
    public Queue tenant_c() {
       return new Queue("tenant_c");
    }

    @Bean
    public Queue tenant_d() {
       return new Queue("tenant_d");
    }

    @Bean
    public Queue resource_lock_tenant_a() {
       return new Queue("resource_lock_tenant_a");
    }

    @Bean
    public Queue resource_lock_tenant_b() {
       return new Queue("resource_lock_tenant_b");
    }

    @Bean
    public Queue resource_lock_tenant_c() {
       return new Queue("resource_lock_tenant_c");
    }

    @Bean
    public Queue resource_lock_tenant_d() {
       return new Queue("resource_lock_tenant_d");
    }
}
