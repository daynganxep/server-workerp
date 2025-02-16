package com.workerp.auth_service.dto.client.response;

import lombok.Data;

@Data
public class UserServiceCreateNewUserResponse {
    String accessToken;
    String refreshToken;
}
