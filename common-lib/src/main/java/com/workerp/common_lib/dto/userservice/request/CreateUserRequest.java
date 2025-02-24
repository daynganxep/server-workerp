package com.workerp.common_lib.dto.userservice.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
    private String email;
    private String password;
    private String fullName;
    private String avatar;
    private Boolean local;
    private String provider;
    private String providerId;
}
