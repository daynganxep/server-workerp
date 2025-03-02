package com.workerp.hr_app_service.message.producer;

import com.workerp.common_lib.dto.message.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange.email}")
    private String exchange;

    @Value("${spring.rabbitmq.routing-key.email}")
    private String routingKey;

    public void sendEmailMessage(EmailMessage emailMessage) {
        rabbitTemplate.convertAndSend(exchange, routingKey, emailMessage);
    }
}