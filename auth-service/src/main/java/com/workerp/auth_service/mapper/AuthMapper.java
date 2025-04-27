package com.workerp.auth_service.mapper;


import com.workerp.common_lib.dto.auth_service.request.AuthChangePasswordRequest;
import com.workerp.common_lib.dto.user_service.request.UserChangePasswordRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    UserChangePasswordRequest toUserChangePasswordRequest(AuthChangePasswordRequest request);
}