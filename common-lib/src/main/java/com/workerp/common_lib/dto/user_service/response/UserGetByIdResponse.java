package com.workerp.common_lib.dto.user_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGetByIdResponse {
    private String id;
    private String email;
    private String fullName;
    private String avatar;
    private Boolean local;
    private String provider;
    private String providerId;
    private Boolean active;
}
