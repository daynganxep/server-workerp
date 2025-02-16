package com.workerp.auth_service.service;

import com.workerp.auth_service.message.producer.EmailMessageProducer;
import com.workerp.auth_service.restapi.UserServiceRestAPI;
import com.workerp.auth_service.dto.request.AuthRegisterRequest;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.message.EmailMessage;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserServiceRestAPI userServiceClient;
    private final EmailMessageProducer emailMessageProducer;
    private final RedisService redisService;

    public void registerRequest(AuthRegisterRequest request) {
        Boolean checkEmailExistedResponse = userServiceClient.checkExistedEmail(request.getEmail());
        if (checkEmailExistedResponse) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Email exited", "auth-f-01");
        }

        // Generate 6-digit code
        String code = CodeGenerator.generateSixDigitCode();

        // Store timetolife in Redis
        redisService.saveWithTTL("auth:register:" + request.getEmail(), code, 10, TimeUnit.MINUTES);

        // Send verification email
        EmailMessage emailMessage = EmailMessage.builder().to(request.getEmail()).type("VERIFY_REGISTER").values(Map.of("code", code)).build();
        emailMessageProducer.sendEmailMessage(emailMessage);
    }
}