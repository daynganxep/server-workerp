package com.workerp.auth_service.mapper;


import com.workerp.common_lib.dto.authservice.request.AuthChangePasswordRequest;
import com.workerp.common_lib.dto.userservice.request.UserChangePasswordRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    UserChangePasswordRequest toUserChangePasswordRequest(AuthChangePasswordRequest request);
}