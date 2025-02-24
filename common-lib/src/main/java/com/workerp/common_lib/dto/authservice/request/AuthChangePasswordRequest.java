package com.workerp.common_lib.dto.authservice.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthChangePasswordRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
