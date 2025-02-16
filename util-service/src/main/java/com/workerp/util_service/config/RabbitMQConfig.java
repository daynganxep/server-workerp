package com.workerp.util_service.config;

import com.workerp.common_lib.config.BaseRabbitMQConfig;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig extends BaseRabbitMQConfig {
    @Value("${spring.rabbitmq.queue.email}")
    private String emailQueue;

    @Value("${spring.rabbitmq.exchange.email}")
    private String emailExchange;

    @Value("${spring.rabbitmq.routing-key.email}")
    private String emailRoutingKey;

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue, true);
    }

    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(emailExchange);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(emailExchange())
                .with(emailRoutingKey);
    }
}