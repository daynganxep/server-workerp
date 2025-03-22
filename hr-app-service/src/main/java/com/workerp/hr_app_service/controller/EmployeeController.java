package com.workerp.hr_app_service.controller;


import com.workerp.common_lib.annotation.CheckPermission;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.hr_app_service.request.HRAppAddOwnerToCompanyRequest;
import com.workerp.common_lib.dto.hr_app_service.request.HRAppInviteToCompanyRequest;
import com.workerp.common_lib.dto.hr_app_service.response.EmployeeResponse;
import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import com.workerp.hr_app_service.service.EmployeeService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/hr-app/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @Value("${app.client.path:'http://localhost:3000/'}")
    private String clientPath;

    @PostMapping("/invite-to-company")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.HR, moduleRole = ModuleRole.MANAGER)
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
    @PermitAll
    public RedirectView verifyInviteToCompany(@PathVariable String code) {
        employeeService.inviteToCompanyVerify(code);
        return new RedirectView(clientPath);
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

    @GetMapping("/company/{companyId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getEmployeesByCompanyId(@PathVariable String companyId) {
        ApiResponse<List<EmployeeResponse>> apiResponse = ApiResponse.<List<EmployeeResponse>>builder()
                .code("hr-app-employee-04")
                .success(true)
                .message("Get all employees by companyid  successfully")
                .data(employeeService.getAllByCompanyId(companyId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllByUser() {
        ApiResponse<List<EmployeeResponse>> apiResponse = ApiResponse.<List<EmployeeResponse>>builder()
                .code("hr-app-employee-05")
                .success(true)
                .message("Get all employees by user  successfully")
                .data(employeeService.getAllByUser())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
