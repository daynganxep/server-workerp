package com.workerp.auth_service.service;

import com.workerp.auth_service.dto.RegisterData;
import com.workerp.auth_service.dto.request.AuthLoginRequest;
import com.workerp.auth_service.dto.request.AuthRefreshTokenRequest;
import com.workerp.auth_service.dto.response.AuthLoginResponse;
import com.workerp.auth_service.dto.response.AuthRefreshTokenResponse;
import com.workerp.auth_service.message.producer.EmailMessageProducer;
import com.workerp.auth_service.restapi.UserServiceRestAPI;
import com.workerp.auth_service.dto.request.AuthRegisterRequest;
import com.workerp.auth_service.util.jwt.AccessTokenUtil;
import com.workerp.auth_service.util.jwt.RefreshTokenUtil;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.jwt.JWTPayload;
import com.workerp.common_lib.dto.message.EmailMessage;
import com.workerp.common_lib.dto.user_service.*;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.service.BaseRedisService;
import com.workerp.common_lib.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserServiceRestAPI userServiceClient;
    private final EmailMessageProducer emailMessageProducer;
    private final BaseRedisService redisService;
    private final RefreshTokenUtil refreshTokenUtil;
    private final AccessTokenUtil accessTokenUtil;

    private String redisRegisterKey(String code) {
        return "auth:register:" + code;
    }

    public void requestRegister(AuthRegisterRequest request) {
        Boolean checkEmailExistedResponse = userServiceClient.checkExistedEmail(request.getEmail()).getData();
        if (checkEmailExistedResponse) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Email exited", "auth-f-01");
        }
        String code = CodeGenerator.generateSixDigitCode();

        RegisterData registerData = new RegisterData(request.getEmail(), request.getPassword());
        redisService.saveWithTTL(redisRegisterKey(code), registerData, 10, TimeUnit.MINUTES);

        EmailMessage emailMessage = EmailMessage
                .builder()
                .to(request.getEmail())
                .type("VERIFY_REGISTER")
                .values(Map.of("code", code))
                .build();
        emailMessageProducer.sendEmailMessage(emailMessage);
    }

    public String verifyRegister(String code) {
        String key = redisRegisterKey(code);
        RegisterData registerData = (RegisterData) redisService.getValue(key);

        if (registerData == null) throw new AppException(HttpStatus.BAD_REQUEST, "Invalid code", "auth-f-02");

        CreateUserRequest createUserRequest = CreateUserRequest
                .builder()
                .email(registerData.getEmail())
                .password(registerData.getPassword())
                .local(true)
                .build();

        CreateUserResponse createUserResponse = userServiceClient.createUser(createUserRequest).getData();
        redisService.delete(key);

        JWTPayload payload = JWTPayload.builder()
                .id(createUserResponse.getId())
                .scope("USER")
                .build();

        return refreshTokenUtil.generateToken(payload);
    }

    public AuthRefreshTokenResponse refreshToken(AuthRefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        JWTPayload payload = refreshTokenUtil.verifyToken(refreshToken);
        String accessToken = accessTokenUtil.generateToken(payload);
        return AuthRefreshTokenResponse.builder().accessToken(accessToken).build();
    }

    public AuthLoginResponse login(AuthLoginRequest request) {
        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .email(request.getEmail())
                .password(request.getPassword()).build();
        UserLoginResponse userLoginResponse = userServiceClient.login(userLoginRequest).getData();

        JWTPayload payload = JWTPayload.builder().id(userLoginResponse.getId()).scope("XXXUSERYYY").build();

        String accessToken = accessTokenUtil.generateToken(payload);
        String refreshToken = refreshTokenUtil.generateToken(payload);

        return AuthLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
    }

    public UserInfoResponse getInfo() {
        return userServiceClient.getInfo().getData();
    }
}