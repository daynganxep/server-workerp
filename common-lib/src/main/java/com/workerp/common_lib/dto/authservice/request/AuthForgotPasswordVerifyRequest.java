package com.workerp.common_lib.dto.authservice.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthForgotPasswordVerifyRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String password;
}
