package com.workerp.hr_app_service.message.producer;

import com.workerp.common_lib.dto.message.EmailMessage;
import com.workerp.common_lib.util.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendEmailMessage(EmailMessage emailMessage) {
        rabbitTemplate.convertAndSend(AppConstant.EMAIL_EXCHANGE, AppConstant.EMAIL_ROUTING_KEY, emailMessage);
    }
}