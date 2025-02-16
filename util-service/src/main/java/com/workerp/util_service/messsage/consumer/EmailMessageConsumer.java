package com.workerp.util_service.messsage.consumer;

import com.workerp.common_lib.dto.message.EmailMessage;
import com.workerp.util_service.enums.EmailType;
import com.workerp.util_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailMessageConsumer {
    private final EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue.email}")
    public void receiveMessage(EmailMessage emailMessage) {
        log.info("Received message: {}", emailMessage);
        switch (EmailType.valueOf(emailMessage.getType())) {
            case VERIFY_REGISTER -> emailService.sendVerificationEmail(emailMessage);
            case WELCOME -> emailService.sendWelcomeEmail(emailMessage);
            case FORGOT_PASSWORD -> emailService.sendVerificationForgotPasswordEmail(emailMessage);
        }
    }
}