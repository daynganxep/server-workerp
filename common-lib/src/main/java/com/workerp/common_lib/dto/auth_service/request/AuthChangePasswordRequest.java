package com.workerp.common_lib.dto.auth_service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthChangePasswordRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
