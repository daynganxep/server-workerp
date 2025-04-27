package com.workerp.common_lib.dto.auth_service.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRegisterRequest {
    @NotNull(message = "Email is required")
    @Email(message  = "Email invalid")
    public String email;

    @NotNull(message = "Password is required")
    public String password;
}
