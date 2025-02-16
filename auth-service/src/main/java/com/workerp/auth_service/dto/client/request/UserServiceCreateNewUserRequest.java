package com.workerp.auth_service.dto.client.request;

import lombok.Data;

@Data
public class UserServiceCreateNewUserRequest {
    String email;
    String password;
}
