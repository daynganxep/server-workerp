package com.workerp.company_app_service.controller;

import com.workerp.common_lib.annotation.CheckPermission;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.company_app_service.reponse.CompanyResponse;
import com.workerp.common_lib.dto.company_app_service.request.CompanyAppCreateCompanyRequest;
import com.workerp.common_lib.dto.company_app_service.request.CompanyAppUpdateCompanyInforRequest;
import com.workerp.common_lib.dto.company_app_service.request.CompanyAppUpdateModules;
import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import com.workerp.company_app_service.service.CompanyService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company-app/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CompanyResponse>> createCompany(@RequestBody @Valid CompanyAppCreateCompanyRequest request) {
        ApiResponse<CompanyResponse> apiResponse = ApiResponse.<CompanyResponse>builder()
                .code("company_app-company-s-01")
                .success(true)
                .message("Company created successfully")
                .data(companyService.createCompany(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/{companyId}")
    @PermitAll
    public ResponseEntity<ApiResponse<CompanyResponse>> getCompanyById(@PathVariable String companyId) {
        ApiResponse<CompanyResponse> apiResponse = ApiResponse.<CompanyResponse>builder()
                .code("company_app-company-s-02")
                .success(true)
                .message("Get company by id successfully")
                .data(companyService.getById(companyId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/{companyId}/update-modules")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.COMPANY, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<CompanyResponse>> updateModules(@RequestBody @Valid CompanyAppUpdateModules request, @PathVariable String companyId) {
        ApiResponse<CompanyResponse> apiResponse = ApiResponse.<CompanyResponse>builder()
                .code("company_app-company-s-03")
                .success(true)
                .message("Update modules successfully")
                .data(companyService.updateModules(companyId, request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> getAllMyCompanies() {
        ApiResponse<List<CompanyResponse>> apiResponse = ApiResponse.<List<CompanyResponse>>builder()
                .code("company_app-company-s-04")
                .success(true)
                .message("Get all my companies")
                .data(companyService.getAllMyCompanies())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.COMPANY, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<CompanyResponse>> updateInfo(@RequestBody @Valid CompanyAppUpdateCompanyInforRequest request) {
        ApiResponse<CompanyResponse> apiResponse = ApiResponse.<CompanyResponse>builder()
                .code("company_app-company-s-05")
                .success(true)
                .message("Updated company info successfully")
                .data(companyService.updateInfo(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
