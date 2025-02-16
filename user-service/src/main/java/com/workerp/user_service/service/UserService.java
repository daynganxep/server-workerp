package com.workerp.user_service.service;

import com.workerp.common_lib.exception.AppException;
import com.workerp.user_service.model.User;
import com.workerp.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Boolean getUserByEmail(String email) {
        return userRepository
                .existsByEmail(email);
    }
}