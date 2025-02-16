package com.workerp.auth_service.restapi;

import com.workerp.common_lib.client.RestApi;
import com.workerp.common_lib.dto.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceRestAPI {
    private final RestApi restApi;

    public Boolean checkExistedEmail(String email) {
        try {
            ApiResponse<Boolean> response = restApi.get("/api/users/email/" + email, Boolean.class);
            return response.getData();
        } catch (Exception e) {
            log.error("Failed to check existed email", e);
            return true;
        }
    }
}