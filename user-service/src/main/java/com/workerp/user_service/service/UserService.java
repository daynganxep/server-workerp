package com.workerp.user_service.service;

import com.workerp.common_lib.dto.user_service.*;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.util.SecurityUtil;
import com.workerp.user_service.mapper.UserMapper;
import com.workerp.user_service.model.User;
import com.workerp.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Boolean getUserByEmail(String email) {
        return userRepository.existsByEmailAndLocalTrue(email);
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        if (getUserByEmail(request.getEmail())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Email already exists", "user-f-01");
        }

        User user = userMapper.createUserRequestToUser(request);
        user.setActive(true);

        if (request.getLocal()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User savedUser = userRepository.save(user);
        return userMapper.userToCreateUserResponse(savedUser);
    }

    public UserLoginResponse login(UserLoginRequest request) {
        String email = request.getEmail();

        User user = userRepository
                .findByEmailAndLocalTrue(email)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Email not found", "user-f-02"));
        String hashedPassword = user.getPassword();

        String password = request.getPassword();
        if (!passwordEncoder.matches(password, hashedPassword)) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Wrong password", "user-f-03");
        }
        return UserLoginResponse.builder().id(user.getId()).build();
    }

    public UserInfoResponse getInfo() {
        String userId = SecurityUtil.getUserId();
        User user =  userRepository.findById(userId).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User not found", "user-f-04"));
        return userMapper.userToUserInfoResponse(user);
    }
}