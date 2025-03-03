package com.workerp.company_app_service.controller;

import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.company_app_service.reponse.CompanyResponse;
import com.workerp.common_lib.dto.company_app_service.request.CompanyAppCreateCompanyRequest;
import com.workerp.common_lib.dto.company_app_service.request.CompanyAppUpdateModules;
import com.workerp.company_app_service.service.CompanyService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company-app/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CompanyResponse>> createCompany(@RequestBody @Valid CompanyAppCreateCompanyRequest request) {
        ApiResponse<CompanyResponse> apiResponse = ApiResponse.<CompanyResponse>builder()
                .code("company-app-company-01")
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
                .code("company-app-company-02")
                .success(true)
                .message("Get company by id successfully")
                .data(companyService.getById(companyId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/{companyId}/update-modules")
    @PermitAll
    public ResponseEntity<ApiResponse<CompanyResponse>> updateModules(@RequestBody @Valid CompanyAppUpdateModules request, @PathVariable String companyId) {
        ApiResponse<CompanyResponse> apiResponse = ApiResponse.<CompanyResponse>builder()
                .code("company-app-company-03")
                .success(true)
                .message("Update modules successfully")
                .data(companyService.updateModules(companyId, request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
