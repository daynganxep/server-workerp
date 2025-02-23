package com.workerp.auth_service.dto.request;

import lombok.Data;

@Data
public class AuthLogOutRequest {
    String refreshToken;
}
