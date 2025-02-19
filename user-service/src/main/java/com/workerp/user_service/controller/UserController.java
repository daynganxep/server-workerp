package com.workerp.user_service.controller;

import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.user_service.CreateUserRequest;
import com.workerp.common_lib.dto.user_service.CreateUserResponse;
import com.workerp.common_lib.dto.user_service.UserLoginRequest;
import com.workerp.common_lib.dto.user_service.UserLoginResponse;
import com.workerp.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/email/{email}")
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
    public ResponseEntity<ApiResponse<UserLoginResponse>> login(@RequestBody UserLoginRequest request) {
        ApiResponse<UserLoginResponse> apiResponse = ApiResponse.<UserLoginResponse>builder()
                .code("user-s-03")
                .success(true)
                .message("Check login successfully")
                .data(userService.login(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}