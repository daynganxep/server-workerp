package com.workerp.common_lib.dto.auth_service.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthForgotPasswordCheckCodeResponse {
    Boolean isCodeValid;
}
