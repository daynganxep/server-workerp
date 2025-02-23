package com.workerp.user_service.mapper;

import com.workerp.common_lib.dto.user_service.*;
import com.workerp.user_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    User createUserRequestToUser(CreateUserRequest request);

    CreateUserResponse userToCreateUserResponse(User user);

    UserInfoResponse userToUserInfoResponse(User user);

    User toUser(UserOAuth2LoginRequest userOAuth2LoginRequest);

    UserOAuth2LoginResponse toUserOAuth2LoginResponse(User user);
}