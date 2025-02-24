package com.workerp.common_lib.dto.authservice.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthForgotPasswordVerifyResponse {
    String refreshToken;
    String accessToken;
}
