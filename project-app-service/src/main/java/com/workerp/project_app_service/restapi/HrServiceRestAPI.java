package com.workerp.project_app_service.restapi;

import com.workerp.common_lib.annotation.ApiGatewayClient;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.hr_app_service.response.EmployeeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ApiGatewayClient
public interface HrServiceRestAPI {
    @GetMapping("/api/hr-app/employees/{employeeId}")
    ApiResponse<EmployeeResponse> getEmployeeById(@PathVariable String employeeId);
}