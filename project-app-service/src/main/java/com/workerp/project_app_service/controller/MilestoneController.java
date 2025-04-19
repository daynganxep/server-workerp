package com.workerp.project_app_service.controller;

import com.workerp.common_lib.annotation.CheckPermission;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.project_app_service.request.MilestoneRequest;
import com.workerp.common_lib.dto.project_app_service.response.MilestoneResponse;
import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import com.workerp.project_app_service.service.MilestoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-app/milestones")
@RequiredArgsConstructor
public class MilestoneController {
    private final MilestoneService milestoneService;

    @PostMapping("/project/{projectId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<MilestoneResponse>> createMilestone(
            @PathVariable String projectId, @RequestBody @Valid MilestoneRequest request) {
        ApiResponse<MilestoneResponse> apiResponse = ApiResponse.<MilestoneResponse>builder()
                .code("milestone-01")
                .success(true)
                .message("Create milestone successfully")
                .data(milestoneService.createMilestone(projectId, request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{milestoneId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<MilestoneResponse>> updateMilestone(
            @PathVariable String milestoneId, @RequestBody @Valid MilestoneRequest request) {
        ApiResponse<MilestoneResponse> apiResponse = ApiResponse.<MilestoneResponse>builder()
                .code("milestone-02")
                .success(true)
                .message("Update milestone successfully")
                .data(milestoneService.updateMilestone(milestoneId, request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{milestoneId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<Void>> deleteMilestone(@PathVariable String milestoneId) {
        milestoneService.deleteMilestone(milestoneId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("milestone-03")
                .success(true)
                .message("Delete milestone successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<MilestoneResponse>>> getMilestonesByProjectId(
            @PathVariable String projectId) {
        ApiResponse<List<MilestoneResponse>> apiResponse = ApiResponse.<List<MilestoneResponse>>builder()
                .code("milestone-04")
                .success(true)
                .message("Get milestones by project successfully")
                .data(milestoneService.getMilestonesByProjectId(projectId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/{milestoneId}/tasks/{taskId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<MilestoneResponse>> addTaskToMilestone(
            @PathVariable String milestoneId, @PathVariable String taskId) {
        ApiResponse<MilestoneResponse> apiResponse = ApiResponse.<MilestoneResponse>builder()
                .code("milestone-05")
                .success(true)
                .message("Add task to milestone successfully")
                .data(milestoneService.addTaskToMilestone(milestoneId, taskId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{milestoneId}/tasks/{taskId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<MilestoneResponse>> removeTaskFromMilestone(
            @PathVariable String milestoneId, @PathVariable String taskId) {
        ApiResponse<MilestoneResponse> apiResponse = ApiResponse.<MilestoneResponse>builder()
                .code("milestone-06")
                .success(true)
                .message("Remove task from milestone successfully")
                .data(milestoneService.removeTaskFromMilestone(milestoneId, taskId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}