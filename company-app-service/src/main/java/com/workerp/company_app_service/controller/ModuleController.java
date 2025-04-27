package com.workerp.company_app_service.controller;

import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.company_app_service.reponse.CompanyAppCreateModuleResponse;
import com.workerp.common_lib.dto.company_app_service.reponse.ModuleResponse;
import com.workerp.common_lib.dto.company_app_service.request.CompanyAppCreateModuleRequest;
import com.workerp.company_app_service.service.ModuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company-app/modules")
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CompanyAppCreateModuleResponse>> createModule(@RequestBody @Valid CompanyAppCreateModuleRequest request) {
        ApiResponse<CompanyAppCreateModuleResponse> apiResponse = ApiResponse.<CompanyAppCreateModuleResponse>builder()
                .code("company_app-module-s-01")
                .success(true)
                .message("Module created successfully")
                .data(moduleService.createModule(request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<ModuleResponse>>> getAllModules() {
        ApiResponse<List<ModuleResponse>> apiResponse = ApiResponse.<List<ModuleResponse>>builder()
                .code("company_app-module-s-02")
                .success(true)
                .message("Get all modules successfully")
                .data(moduleService.getAllModules())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
