package com.workerp.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRegisterRequest {
    @NotNull(message = "Email is required")
    @Email(message  = "Email invalid")
    public String email;
}
