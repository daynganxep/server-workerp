package com.workerp.hr_app_service.restapi;

import com.workerp.common_lib.annotation.ApiGatewayClient;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.user_service.response.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ApiGatewayClient
public interface UserServiceRestAPI {
    @GetMapping("/api/users/{userId}")
    ApiResponse<UserGetByIdResponse> getUserById(@PathVariable String userId);
}