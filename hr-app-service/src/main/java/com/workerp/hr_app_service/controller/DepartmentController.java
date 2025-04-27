package com.workerp.hr_app_service.controller;

import com.workerp.common_lib.annotation.CheckPermission;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.hr_app_service.request.DepartmentRequest;
import com.workerp.common_lib.dto.hr_app_service.response.DepartmentResponse;
import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import com.workerp.hr_app_service.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hr-app/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/company")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.HR, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(@RequestBody @Valid DepartmentRequest request) {
        ApiResponse<DepartmentResponse> apiResponse = ApiResponse.<DepartmentResponse>builder()
                .code("hr_app-dpm-s-01")
                .success(true)
                .message("Create department successfully")
                .data(departmentService.createDepartment(request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{departmentId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.HR, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(
            @PathVariable String departmentId, @RequestBody @Valid DepartmentRequest request) {
        ApiResponse<DepartmentResponse> apiResponse = ApiResponse.<DepartmentResponse>builder()
                .code("hr_app-dpm-s-02")
                .success(true)
                .message("Update department successfully")
                .data(departmentService.updateDepartment(departmentId, request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{departmentId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.HR, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable String departmentId) {
        departmentService.deleteDepartment(departmentId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("hr_app-dpm-s-03")
                .success(true)
                .message("Delete department successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.HR, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getDepartmentsByCompanyId(
            @PathVariable String companyId) {
        ApiResponse<List<DepartmentResponse>> apiResponse = ApiResponse.<List<DepartmentResponse>>builder()
                .code("hr_app-dpm-s-04")
                .success(true)
                .message("Get departments by company successfully")
                .data(departmentService.getAllByCompanyId(companyId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}