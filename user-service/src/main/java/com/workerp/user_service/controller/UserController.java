package com.workerp.user_service.controller;

import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.userservice.request.*;
import com.workerp.common_lib.dto.userservice.response.*;
import com.workerp.user_service.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/email/{email}")
    @PermitAll
    public ResponseEntity<ApiResponse<Boolean>> checkExistedEmail(@PathVariable String email) {
        ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                .code("user-s-01")
                .success(true)
                .message("User found by email")
                .data(userService.getUserByEmail(email))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping
    @PermitAll
    public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(@RequestBody CreateUserRequest request) {
        ApiResponse<CreateUserResponse> apiResponse = ApiResponse.<CreateUserResponse>builder()
                .code("user-s-02")
                .success(true)
                .message("User created")
                .data(userService.createUser(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<ApiResponse<UserLoginResponse>> login(@RequestBody UserLoginRequest request) {
        ApiResponse<UserLoginResponse> apiResponse = ApiResponse.<UserLoginResponse>builder()
                .code("user-s-03")
                .success(true)
                .message("Check login successfully")
                .data(userService.login(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserInfoResponse>> info() {
        ApiResponse<UserInfoResponse> apiResponse = ApiResponse.<UserInfoResponse>builder()
                .code("user-s-04")
                .success(true)
                .message("Get user info successfully")
                .data(userService.getInfo())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody UserChangePasswordRequest request) {
        userService.changePassword(request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("user-s-05")
                .success(true)
                .message("Change password successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/oauth2-login")
    @PermitAll
    public ResponseEntity<ApiResponse<UserOAuth2LoginResponse>> oauth2Login(@RequestBody @Valid UserOAuth2LoginRequest request) {
        ApiResponse<UserOAuth2LoginResponse> apiResponse = ApiResponse.<UserOAuth2LoginResponse>builder()
                .code("user-s-06")
                .success(true)
                .message("Oauth2 login successfully")
                .data(userService.oauth2Login(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/forgot-password")
    @PermitAll
    public ResponseEntity<ApiResponse<UserForgotPasswordResponse>> oauth2Login(@RequestBody @Valid UserForgotPasswordRequest request) {
        ApiResponse<UserForgotPasswordResponse> apiResponse = ApiResponse.<UserForgotPasswordResponse>builder()
                .code("user-s-07")
                .success(true)
                .message("Forgot password successfully")
                .data(userService.forgotPassword(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}