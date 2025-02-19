package com.workerp.auth_service.restapi;

import com.workerp.common_lib.annotation.ApiGatewayClient;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.user_service.CreateUserRequest;
import com.workerp.common_lib.dto.user_service.CreateUserResponse;
import com.workerp.common_lib.dto.user_service.UserLoginRequest;
import com.workerp.common_lib.dto.user_service.UserLoginResponse;
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
}