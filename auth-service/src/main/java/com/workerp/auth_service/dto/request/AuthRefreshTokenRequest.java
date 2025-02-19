package com.workerp.auth_service.dto.request;

import lombok.Data;

@Data
public class AuthRefreshTokenRequest {
    String refreshToken;
}
