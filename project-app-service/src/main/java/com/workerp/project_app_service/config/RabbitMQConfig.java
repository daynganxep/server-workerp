package com.workerp.project_app_service.config;

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

    @Bean
    public Queue addEmployeeQueue() {
        return new Queue(AppConstant.ADD_EMPLOYEE_QUEUE, true);
    }

    @Bean
    public DirectExchange addEmployeeExchange() {
        return new DirectExchange(AppConstant.ADD_EMPLOYEE_EXCHANGE);
    }

    @Bean
    public Binding addEmployeeBinding() {
        return BindingBuilder
                .bind(addEmployeeQueue())
                .to(addEmployeeExchange())
                .with(AppConstant.ADD_EMPLOYEE_ROUTING_KEY);
    }
}