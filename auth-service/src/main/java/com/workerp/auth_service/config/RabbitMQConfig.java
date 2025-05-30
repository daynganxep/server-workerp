package com.workerp.auth_service.config;

import com.workerp.common_lib.config.BaseRabbitMQConfig;
import com.workerp.common_lib.util.AppConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig extends BaseRabbitMQConfig {
    @Bean
    public Queue emailQueue() {
        return new Queue(AppConstant.EMAIL_QUEUE, true);
    }

    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(AppConstant.EMAIL_EXCHANGE);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(emailExchange())
                .with(AppConstant.EMAIL_ROUTING_KEY);
    }
}