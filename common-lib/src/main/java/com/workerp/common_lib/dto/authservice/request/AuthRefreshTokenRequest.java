package com.workerp.common_lib.dto.authservice.request;

import lombok.Data;

@Data
public class AuthRefreshTokenRequest {
    String refreshToken;
}
