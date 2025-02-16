package com.workerp.user_service.controller;

import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.user_service.model.User;
import com.workerp.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> findByEmail(@PathVariable String email) {
        ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                .code("user-s-01")
                .message("User update information success")
                .data(userService.getUserByEmail(email))
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}