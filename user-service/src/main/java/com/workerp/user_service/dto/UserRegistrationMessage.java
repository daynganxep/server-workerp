package com.workerp.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationMessage {
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
}