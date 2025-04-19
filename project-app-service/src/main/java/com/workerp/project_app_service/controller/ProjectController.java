package com.workerp.project_app_service.controller;

import com.workerp.common_lib.annotation.CheckPermission;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.project_app_service.request.ProjectMemberRequest;
import com.workerp.common_lib.dto.project_app_service.request.ProjectRequest;
import com.workerp.common_lib.dto.project_app_service.response.ProjectResponse;
import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import com.workerp.common_lib.enums.project_app_service.ProjectMemberRole;
import com.workerp.project_app_service.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-app/projects")
@RequiredArgsConstructor
public class ProjectController {
        private final ProjectService projectService;

        @PostMapping
        @PreAuthorize("isAuthenticated()")
        @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
        public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@RequestBody @Valid ProjectRequest request) {
                ApiResponse<ProjectResponse> apiResponse = ApiResponse.<ProjectResponse>builder()
                        .code("project-01")
                        .success(true)
                        .message("Create project successfully")
                        .data(projectService.createProject(request))
                        .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }

        @PutMapping("/{projectId}")
        @PreAuthorize("isAuthenticated()")
        @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
        public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
                @PathVariable String projectId, @RequestBody @Valid ProjectRequest request) {
                ApiResponse<ProjectResponse> apiResponse = ApiResponse.<ProjectResponse>builder()
                        .code("project-02")
                        .success(true)
                        .message("Update project successfully")
                        .data(projectService.updateProject(projectId, request))
                        .build();
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }

        @DeleteMapping("/{projectId}")
        @PreAuthorize("isAuthenticated()")
        @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
        public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable String projectId) {
                projectService.deleteProject(projectId);
                ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                        .code("project-03")
                        .success(true)
                        .message("Delete project successfully")
                        .build();
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }

        @GetMapping("/company/{companyId}")
        @PreAuthorize("isAuthenticated()")
        @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
        public ResponseEntity<ApiResponse<List<ProjectResponse>>> getProjectsByCompanyId(
                @PathVariable String companyId) {
                ApiResponse<List<ProjectResponse>> apiResponse = ApiResponse.<List<ProjectResponse>>builder()
                        .code("project-04")
                        .success(true)
                        .message("Get projects by company successfully")
                        .data(projectService.getProjectsByCompanyId(companyId))
                        .build();
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }

        @PostMapping("/{projectId}/members")
        @PreAuthorize("isAuthenticated()")
        @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
        public ResponseEntity<ApiResponse<ProjectResponse>> addMember(
                @PathVariable String projectId, @RequestBody @Valid ProjectMemberRequest request) {
                ApiResponse<ProjectResponse> apiResponse = ApiResponse.<ProjectResponse>builder()
                        .code("project-05")
                        .success(true)
                        .message("Add member successfully")
                        .data(projectService.addMember(projectId, request))
                        .build();
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }

        @DeleteMapping("/{projectId}/members/{employeeId}")
        @PreAuthorize("isAuthenticated()")
        @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
        public ResponseEntity<ApiResponse<ProjectResponse>> removeMember(
                @PathVariable String projectId, @PathVariable String employeeId) {
                ApiResponse<ProjectResponse> apiResponse = ApiResponse.<ProjectResponse>builder()
                        .code("project-06")
                        .success(true)
                        .message("Remove member successfully")
                        .data(projectService.removeMember(projectId, employeeId))
                        .build();
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }

        @GetMapping("/me")
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<ApiResponse<List<ProjectResponse>>> getMyProjects() {
                ApiResponse<List<ProjectResponse>> apiResponse = ApiResponse.<List<ProjectResponse>>builder()
                        .code("project-07")
                        .success(true)
                        .message("Get my projects successfully")
                        .data(projectService.getMyProjects())
                        .build();
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }

        @GetMapping("/{projectId}")
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(@PathVariable String projectId) {
                ApiResponse<ProjectResponse> apiResponse = ApiResponse.<ProjectResponse>builder()
                        .code("project-08")
                        .success(true)
                        .message("Get project successfully")
                        .data(projectService.getProjectById(projectId))
                        .build();
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }

        @PutMapping("/{projectId}/members/{employeeId}/{role}")
        @PreAuthorize("isAuthenticated()")
        @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
        public ResponseEntity<ApiResponse<Void>> updateMemberRole(
                @PathVariable String projectId, @PathVariable String employeeId, @PathVariable ProjectMemberRole role) {
                projectService.updateMemberRole(projectId, employeeId, role);
                ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                        .code("project-09")
                        .success(true)
                        .message("Update member role successfully")
                        .build();
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
}