package com.workerp.company_app_service.controller;

import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.company_app_service.reponse.CompanyModuleRoleResponse;
import com.workerp.company_app_service.service.CompanyModuleRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company-app/company-module-roles")
@RequiredArgsConstructor
public class CompanyModuleRoleController {
    private final CompanyModuleRoleService companyModuleRoleService;

    @GetMapping("/company/{companyId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<CompanyModuleRoleResponse>>> getAllByCompanyId(@PathVariable String companyId) {
        ApiResponse<List<CompanyModuleRoleResponse>> apiResponse = ApiResponse.<List<CompanyModuleRoleResponse>>builder()
                .code("company-app-cmr-01")
                .success(true)
                .message("Get all company module roles successfully")
                .data(companyModuleRoleService.getAll(companyId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
