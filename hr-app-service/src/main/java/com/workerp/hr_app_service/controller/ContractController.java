package com.workerp.hr_app_service.controller;

import com.workerp.common_lib.annotation.CheckPermission;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.hr_app_service.request.ContractRequest;
import com.workerp.common_lib.dto.hr_app_service.response.ContractResponse;
import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import com.workerp.hr_app_service.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hr-app/contracts")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @PostMapping("/employee")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.HR, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<ContractResponse>> createContract(@RequestBody @Valid ContractRequest request) {
        ApiResponse<ContractResponse> apiResponse = ApiResponse.<ContractResponse>builder()
                .code("hr-app-ctr-01")
                .success(true)
                .message("Create contract successfully")
                .data(contractService.createContract(request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{contractId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.HR, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<ContractResponse>> updateContract(
            @PathVariable String contractId, @RequestBody @Valid ContractRequest request) {
        ApiResponse<ContractResponse> apiResponse = ApiResponse.<ContractResponse>builder()
                .code("hr-app-ctr-02")
                .success(true)
                .message("Update contract successfully")
                .data(contractService.updateContract(contractId, request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{contractId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.HR, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<Void>> deleteContract(@PathVariable String contractId) {
        contractService.deleteContract(contractId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("hr-app-ctr-03")
                .success(true)
                .message("Delete contract successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<ContractResponse>>> getContractsByEmployeeId(
            @PathVariable String employeeId) {
        ApiResponse<List<ContractResponse>> apiResponse = ApiResponse.<List<ContractResponse>>builder()
                .code("hr-app-ctr-04")
                .success(true)
                .message("Get contracts by employee successfully")
                .data(contractService.getAllByEmployeeId(employeeId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.HR, moduleRole = ModuleRole.USER)
    public ResponseEntity<ApiResponse<List<ContractResponse>>> getMyContracts() {
        ApiResponse<List<ContractResponse>> apiResponse = ApiResponse.<List<ContractResponse>>builder()
                .code("hr-app-ctr-05")
                .success(true)
                .message("Get my contract successfully")
                .data(contractService.getMyContracts())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}