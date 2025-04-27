package com.workerp.common_lib.dto.auth_service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthForgotPasswordVerifyRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String password;
}
