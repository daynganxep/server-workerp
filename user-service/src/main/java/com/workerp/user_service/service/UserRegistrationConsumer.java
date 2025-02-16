package com.workerp.user_service.service;

import com.workerp.user_service.dto.UserRegistrationMessage;
import com.workerp.user_service.model.User;
import com.workerp.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistrationConsumer {

    private final UserRepository userRepository;

    @RabbitListener(queues = "${spring.rabbitmq.queue.user-registration}")
    public void handleUserRegistration(UserRegistrationMessage message) {
        log.info("Received registration message for user: {}", message.getUsername());
        
        try {
            User newUser = User.builder()
                    .username(message.getUsername())
                    .email(message.getEmail())
                    .fullName(message.getFullName())
                    .phoneNumber(message.getPhoneNumber())
                    .address(message.getAddress())  
                    .active(true)
                    .build();

            userRepository.save(newUser);
            log.info("Successfully created user: {}", newUser.getUsername());
            
        } catch (Exception e) {
            log.error("Error processing user registration: {}", e.getMessage(), e);
            throw e; // Will trigger message requeue
        }
    }
}