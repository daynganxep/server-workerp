package com.workerp.user_service.service;

import com.workerp.common_lib.dto.user_service.request.*;
import com.workerp.common_lib.dto.user_service.response.*;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.util.SecurityUtil;
import com.workerp.user_service.mapper.UserMapper;
import com.workerp.user_service.model.User;
import com.workerp.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            throw new AppException(HttpStatus.BAD_REQUEST, "Email already exists", "user-f-02-01");
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
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Email not found", "user-f-03-01"));
        String hashedPassword = user.getPassword();

        String password = request.getPassword();
        if (!passwordEncoder.matches(password, hashedPassword)) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Wrong password", "user-f-03-02");
        }
        return UserLoginResponse.builder().id(user.getId()).build();
    }

    public UserInfoResponse getInfo() {
        String userId = SecurityUtil.getUserId();
        User user =  userRepository.findById(userId).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User not found", "user-f-04-01"));
        return userMapper.userToUserInfoResponse(user);
    }

    public void changePassword(UserChangePasswordRequest request) {
        String userId = SecurityUtil.getUserId();
        User user = userRepository.findByIdAndLocalTrue(userId).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User not found", "user-f-05-01"));
        String hashedPassword = user.getPassword();
        String oldPassword = request.getOldPassword();
        if (!passwordEncoder.matches(oldPassword, hashedPassword)) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Wrong old password", "user-f-05-02");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public UserOAuth2LoginResponse oauth2Login(UserOAuth2LoginRequest request){
        String providerName = request.getProvider();
        String providerId = request.getProviderId();
        Optional<User> userOptional = userRepository.findByProviderAndProviderId(providerName, providerId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return userMapper.toUserOAuth2LoginResponse(user);
        }
        User user = userMapper.toUser(request);
        userRepository.save(user);
        return userMapper.toUserOAuth2LoginResponse(user);
    }

    public UserForgotPasswordResponse forgotPassword(UserForgotPasswordRequest request) {
        String email = request.getEmail();
        User user = userRepository.findByEmailAndLocalTrue(email).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Email not found", "user-f-08-01"));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return userMapper.toUserForgotPasswordResponse(user);
    }

    public UserGetByIdResponse getById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User not found", "user-f-09-01"));
        return userMapper.toUserGetByIdResponse(user);
    }
}