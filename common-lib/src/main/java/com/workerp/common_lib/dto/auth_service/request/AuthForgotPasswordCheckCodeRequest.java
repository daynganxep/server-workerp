package com.workerp.common_lib.dto.auth_service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthForgotPasswordCheckCodeRequest {
    @NotBlank
    String code;
}
