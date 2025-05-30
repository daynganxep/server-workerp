package com.workerp.auth_service.restapi;

import com.workerp.common_lib.annotation.ApiGatewayClient;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.user_service.request.*;
import com.workerp.common_lib.dto.user_service.response.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiGatewayClient
public interface UserServiceRestAPI {
    @GetMapping("/api/users/email/{email}")
    ApiResponse<Boolean> checkExistedEmail(@PathVariable String email);

    @PostMapping("/api/users")
    ApiResponse<CreateUserResponse> createUser(@RequestBody CreateUserRequest request);

    @PostMapping("/api/users/login")
    ApiResponse<UserLoginResponse> login(@RequestBody UserLoginRequest request);

    @GetMapping("/api/users/info")
    ApiResponse<UserInfoResponse> getInfo();

    @PostMapping("/api/users/change-password")
    ApiResponse<Void> changePassword(@RequestBody UserChangePasswordRequest request);

    @PostMapping("/api/users/oauth2-login")
    ApiResponse<UserOAuth2LoginResponse> oAuth2Login(UserOAuth2LoginRequest request);

    @PostMapping("/api/users/forgot-password")
    ApiResponse<UserForgotPasswordResponse> forgotPassword(@RequestBody UserForgotPasswordRequest request);
}