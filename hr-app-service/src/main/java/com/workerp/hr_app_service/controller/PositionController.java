package com.workerp.hr_app_service.controller;

import com.workerp.common_lib.annotation.CheckPermission;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.hr_app_service.request.PositionRequest;
import com.workerp.common_lib.dto.hr_app_service.response.PositionResponse;
import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import com.workerp.hr_app_service.service.PositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hr-app/positions")
@RequiredArgsConstructor
public class PositionController {
    private final PositionService positionService;

    @PostMapping("/company")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.HR, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<PositionResponse>> createPosition(@RequestBody @Valid PositionRequest request) {
        ApiResponse<PositionResponse> apiResponse = ApiResponse.<PositionResponse>builder()
                .code("hr_app-pos-s-01")
                .success(true)
                .message("Create position successfully")
                .data(positionService.createPosition(request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{positionId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.HR, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<PositionResponse>> updatePosition(
            @PathVariable String positionId, @RequestBody @Valid PositionRequest request) {
        ApiResponse<PositionResponse> apiResponse = ApiResponse.<PositionResponse>builder()
                .code("hr_app-pos-s-02")
                .success(true)
                .message("Update position successfully")
                .data(positionService.updatePosition(positionId, request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{positionId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.HR, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<Void>> deletePosition(@PathVariable String positionId) {
        positionService.deletePosition(positionId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("hr_app-pos-s-03")
                .success(true)
                .message("Delete position successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.HR, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<List<PositionResponse>>> getPositionsByCompanyId(
            @PathVariable String companyId) {
        ApiResponse<List<PositionResponse>> apiResponse = ApiResponse.<List<PositionResponse>>builder()
                .code("hr_app-pos-s-04")
                .success(true)
                .message("Get positions by company successfully")
                .data(positionService.getAllByCompanyId(companyId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}