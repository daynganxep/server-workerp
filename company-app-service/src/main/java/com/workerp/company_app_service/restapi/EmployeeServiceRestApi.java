package com.workerp.company_app_service.restapi;

import com.workerp.common_lib.annotation.ApiGatewayClient;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.hr_app_service.request.HRAppAddOwnerToCompanyRequest;
import com.workerp.common_lib.dto.hr_app_service.response.EmployeeResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiGatewayClient
public interface EmployeeServiceRestApi {
    @PostMapping("/api/hr-app/employees/add-owner-to-company")
    ApiResponse<EmployeeResponse> addOwnerToCompany(@RequestBody @Valid HRAppAddOwnerToCompanyRequest request);
}
