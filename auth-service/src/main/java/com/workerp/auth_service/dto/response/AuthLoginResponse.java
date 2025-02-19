package com.workerp.auth_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthLoginResponse {
    String refreshToken;
    String accessToken;
}
