package com.workerp.common_lib.dto.user_service.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {
    String email;
    String password;
}
