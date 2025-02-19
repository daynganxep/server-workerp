package com.workerp.auth_service.dto.request;

import lombok.Data;

@Data
public class AuthLoginRequest {
    String email;
    String password;
}
