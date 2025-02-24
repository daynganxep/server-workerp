package com.workerp.common_lib.dto.authservice.request;

import lombok.Data;

@Data
public class AuthLoginRequest {
    String email;
    String password;
}
