package com.workerp.common_lib.dto.userservice.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOAuth2LoginRequest {
    @Email
    private String email;
    @NotBlank
    private String fullName;
    @NotBlank
    private String avatar;
    private Boolean local;
    @NotBlank
    private String provider;
    @NotBlank
    private String providerId;
    private Boolean active;
}
