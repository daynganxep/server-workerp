package com.workerp.auth_service.mapper;


import com.workerp.auth_service.dto.request.AuthChangePasswordRequest;
import com.workerp.common_lib.dto.user_service.UserChangePasswordRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    UserChangePasswordRequest toUserChangePasswordRequest(AuthChangePasswordRequest request);
}