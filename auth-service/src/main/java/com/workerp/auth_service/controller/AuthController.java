package com.workerp.auth_service.controller;

import com.workerp.auth_service.dto.request.AuthRegisterRequest;
import com.workerp.auth_service.service.AuthService;
import com.workerp.common_lib.dto.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RestTemplate restTemplate;

    private  final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> registerRequest(@RequestBody @Valid AuthRegisterRequest request) {
        authService.registerRequest(request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("auth-s-01")
                .message("Request register successfully, check your email")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}