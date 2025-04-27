package com.workerp.common_lib.dto.auth_service.request;

import lombok.Data;

@Data
public class AuthLoginRequest {
    String email;
    String password;
}
