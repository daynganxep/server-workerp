package com.workerp.common_lib.dto.auth_service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
}