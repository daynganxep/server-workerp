package com.workerp.common_lib.dto.userservice.response;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserInfoResponse {
    private String id;
    private String email;
    private String fullName;
    private String avatar;
    private Boolean local;
    private String provider;
    private String providerId;
    private Boolean active;
}
