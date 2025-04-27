package com.workerp.company_app_service.controller;

import com.workerp.common_lib.annotation.CheckPermission;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.company_app_service.reponse.CompanyModuleRoleResponse;
import com.workerp.common_lib.dto.company_app_service.request.CompanyModuleRoleModifyRequest;
import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import com.workerp.company_app_service.service.CompanyModuleRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company-app/company-module-roles")
@RequiredArgsConstructor
public class CompanyModuleRoleController {
    private final CompanyModuleRoleService companyModuleRoleService;

    @GetMapping("/company")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.COMPANY, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<List<CompanyModuleRoleResponse>>> getAllByCompanyId() {
        ApiResponse<List<CompanyModuleRoleResponse>> apiResponse = ApiResponse.<List<CompanyModuleRoleResponse>>builder().code("company_app-cmr-s-01").success(true).message("Get all company module roles successfully").data(companyModuleRoleService.getAll()).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/company/{companyId}/employee")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<CompanyModuleRoleResponse>>> getByEmployee(@PathVariable String companyId) {
        ApiResponse<List<CompanyModuleRoleResponse>> apiResponse = ApiResponse.<List<CompanyModuleRoleResponse>>builder().code("company_app-cmr-s-02").success(true).message("Get company module roles by employee successfully").data(companyModuleRoleService.getByEmployee(companyId)).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/company")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.COMPANY, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<List<CompanyModuleRoleResponse>>> modifyMany(@RequestBody @Valid List<CompanyModuleRoleModifyRequest> companyModuleRoleModifyRequests) {
        ApiResponse<List<CompanyModuleRoleResponse>> apiResponse = ApiResponse.<List<CompanyModuleRoleResponse>>builder().code("company_app-cmr-s-03").success(true).message("Modify many company module roles successfully").data(companyModuleRoleService.modifyMany(companyModuleRoleModifyRequests)).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
