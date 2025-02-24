package com.workerp.auth_service.service;

import com.workerp.common_lib.dto.authservice.request.*;
import com.workerp.common_lib.dto.authservice.response.AuthForgotPasswordCheckCodeResponse;
import com.workerp.common_lib.dto.authservice.response.AuthForgotPasswordVerifyResponse;
import com.workerp.common_lib.dto.authservice.response.AuthLoginResponse;
import com.workerp.common_lib.dto.authservice.response.AuthRefreshTokenResponse;
import com.workerp.auth_service.mapper.AuthMapper;
import com.workerp.auth_service.message.producer.EmailMessageProducer;
import com.workerp.auth_service.restapi.UserServiceRestAPI;
import com.workerp.auth_service.util.jwt.AccessTokenUtil;
import com.workerp.auth_service.util.jwt.RefreshTokenUtil;
import com.workerp.common_lib.dto.jwt.JWTPayload;
import com.workerp.common_lib.dto.message.EmailMessage;
import com.workerp.common_lib.dto.userservice.request.CreateUserRequest;
import com.workerp.common_lib.dto.userservice.request.UserForgotPasswordRequest;
import com.workerp.common_lib.dto.userservice.request.UserLoginRequest;
import com.workerp.common_lib.dto.userservice.request.UserOAuth2LoginRequest;
import com.workerp.common_lib.dto.userservice.response.*;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.service.BaseRedisService;
import com.workerp.common_lib.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    private final AuthMapper authMapper;

    private String redisRegisterKey(String code) {
        return "auth:register:" + code;
    }

    private String redisForgotPasswordKey(String code) {
        return "auth:forgot-password:" + code;
    }

    public void requestRegister(AuthRegisterRequest request) {
        Boolean checkEmailExistedResponse = userServiceClient.checkExistedEmail(request.getEmail()).getData();
        if (checkEmailExistedResponse) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Email exited", "auth-f-01-01");
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

        if (registerData == null) throw new AppException(HttpStatus.BAD_REQUEST, "Invalid code", "auth-f-02-01");

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

    public void logOut(AuthLogOutRequest request) {
        String refreshToken = request.getRefreshToken();
        JWTPayload payload = refreshTokenUtil.verifyToken(refreshToken);
        refreshTokenUtil.deleteToken(payload.getId());
    }

    public void changePassword(AuthChangePasswordRequest request) {
        userServiceClient.changePassword(authMapper.toUserChangePasswordRequest(request));
    }

    public String loginOAuth2Success(OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Invalid OAuth2 user", "auth-f-07-01");
        }

        String accountOAuthId;
        String providerName = oAuth2User.getAttribute("provider"); // Có thể null với GitHub
        String email;
        String avatar;
        String name;

        switch (providerName) {
            case "google" -> {
                accountOAuthId = oAuth2User.getAttribute("sub");
                email = oAuth2User.getAttribute("email");
                avatar = oAuth2User.getAttribute("picture");
                name = oAuth2User.getAttribute("name");
            }
            case "github" -> {
                accountOAuthId = String.valueOf(oAuth2User.<String>getAttribute("id"));
                email = oAuth2User.getAttribute("email");
                avatar = oAuth2User.getAttribute("avatar_url");
                name = oAuth2User.getAttribute("name");
            }
            default -> throw new AppException(HttpStatus.BAD_REQUEST, "Unsupported OAuth2 provider", "auth-f-07-02");
        }

        if (email == null || email.isBlank()) {
            email = providerName + "-" + accountOAuthId + "@noemail.com";
        }

        UserOAuth2LoginRequest userOAuth2Login = UserOAuth2LoginRequest.builder()
                .email(email)
                .fullName(name != null ? name : "User")
                .active(true)
                .avatar(avatar)
                .providerId(accountOAuthId)
                .provider(providerName)
                .local(false)
                .build();

        UserOAuth2LoginResponse userOAuth2LoginResponse = userServiceClient.oAuth2Login(userOAuth2Login).getData();
        JWTPayload payload = JWTPayload.builder()
                .id(userOAuth2LoginResponse.getId())
                .scope("USER")
                .build();

        return refreshTokenUtil.generateToken(payload);
    }

    public void forgotPassword(AuthForgotPasswordRequest request) {
        String email = request.getEmail();
        Boolean checkEmailExistedResponse = userServiceClient.checkExistedEmail(email).getData();
        if (!checkEmailExistedResponse) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Email does not exist", "auth-f-08-01");
        }
        String code = CodeGenerator.generateSixDigitCode();
        String key = redisForgotPasswordKey(code);
        redisService.saveWithTTL(key, email, 10, TimeUnit.MINUTES);
        emailMessageProducer.sendEmailMessage(EmailMessage.builder()
                .to(email)
                .type("FORGOT_PASSWORD")
                .values(Map.of("code", code))
                .build());
    }

    public AuthForgotPasswordCheckCodeResponse forgotPasswordCheckCode(AuthForgotPasswordCheckCodeRequest request) {
        String key = redisForgotPasswordKey(request.getCode());
        Boolean isCodeValid = redisService.getRedisTemplate().hasKey(key);
        return AuthForgotPasswordCheckCodeResponse.builder().isCodeValid(isCodeValid).build();
    }

    public AuthForgotPasswordVerifyResponse verifyForgotPassword(AuthForgotPasswordVerifyRequest request) {
        String key = redisForgotPasswordKey(request.getCode());
        String email = (String) redisService.getValue(key);
        if (email == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Invalid code", "auth-f-09-01");
        }
        UserForgotPasswordResponse userForgotPasswordResponse = userServiceClient.forgotPassword(UserForgotPasswordRequest.builder().email(email).password(request.getPassword()).build()).getData();
        redisService.delete(key);
        JWTPayload payload = JWTPayload.builder().id(userForgotPasswordResponse.getId()).scope("USER").build();
        String refreshToken = refreshTokenUtil.generateToken(payload);
        String accessToken = accessTokenUtil.generateToken(payload);
        return AuthForgotPasswordVerifyResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }
}