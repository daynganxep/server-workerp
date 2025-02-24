package com.workerp.common_lib.dto.userservice.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserForgotPasswordRequest {
    @Email
    @NotBlank
    String email;
    @NotBlank
    String password;
}
