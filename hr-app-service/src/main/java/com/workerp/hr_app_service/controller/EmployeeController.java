package com.workerp.hr_app_service.controller;


import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.hr_app_service.request.HRAppAddOwnerToCompanyRequest;
import com.workerp.common_lib.dto.hr_app_service.request.HRAppInviteToCompanyRequest;
import com.workerp.common_lib.dto.hr_app_service.response.EmployeeResponse;
import com.workerp.hr_app_service.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hr-app/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/invite-to-company")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> inviteToCompany(@RequestBody @Valid HRAppInviteToCompanyRequest request) {
        employeeService.inviteToCompany(request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("hr-app-employee-01")
                .success(true)
                .message("Invite to company successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/invite-to-company/verify/{code}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> verifyInviteToCompany(@PathVariable String code) {
        employeeService.inviteToCompanyVerify(code);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("hr-app-employee-02")
                .success(true)
                .message("Invite to company successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/add-owner-to-company")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<EmployeeResponse>> addOwnerToCompany(@RequestBody HRAppAddOwnerToCompanyRequest request) {
        ApiResponse<EmployeeResponse> apiResponse = ApiResponse.<EmployeeResponse>builder()
                .code("hr-app-employee-03")
                .success(true)
                .message("Add owner to company successfully")
                .data(employeeService.addOwnerToCompany(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
