package com.workerp.common_lib.dto.auth_service.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthForgotPasswordRequest {
    @Email
    @NotBlank
    private String email;
}
