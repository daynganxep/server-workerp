package com.workerp.project_app_service.controller;

import com.workerp.common_lib.annotation.CheckPermission;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.project_app_service.request.AssigneeUpdateTaskRequest;
import com.workerp.common_lib.dto.project_app_service.request.CommentRequest;
import com.workerp.common_lib.dto.project_app_service.request.TaskRequest;
import com.workerp.common_lib.dto.project_app_service.response.CommentResponse;
import com.workerp.common_lib.dto.project_app_service.response.TaskResponse;
import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import com.workerp.common_lib.enums.project_app_service.TaskPriority;
import com.workerp.common_lib.enums.project_app_service.TaskStatus;
import com.workerp.common_lib.exception.AppException;
import com.workerp.project_app_service.dto.TaskUpdateStatus;
import com.workerp.project_app_service.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-app/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/project/{projectId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(
            @PathVariable String projectId,
            @RequestBody @Valid TaskRequest request) {
        ApiResponse<TaskResponse> apiResponse = ApiResponse.<TaskResponse>builder()
                .code("task-01")
                .success(true)
                .message("Create task successfully")
                .data(taskService.createTask(projectId, request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(
            @PathVariable String taskId,
            @RequestBody @Valid TaskRequest request) {
        ApiResponse<TaskResponse> apiResponse = ApiResponse.<TaskResponse>builder()
                .code("task-02")
                .success(true)
                .message("Update task successfully")
                .data(taskService.updateTask(taskId, request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable String taskId) {
        taskService.deleteTask(taskId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("task-03")
                .success(true)
                .message("Delete task successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByProjectId(
            @PathVariable String projectId,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(defaultValue = "dueDate") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        ApiResponse<List<TaskResponse>> apiResponse = ApiResponse.<List<TaskResponse>>builder()
                .code("task-04")
                .success(true)
                .message("Get tasks by project successfully")
                .data(taskService.getTasksByProjectId(projectId, status, priority, sortBy, order))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/project/{projectId}/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getMyTasks(
            @PathVariable String projectId,
            @RequestParam(defaultValue = "dueDate") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        ApiResponse<List<TaskResponse>> apiResponse = ApiResponse.<List<TaskResponse>>builder()
                .code("task-05")
                .success(true)
                .message("Get my tasks successfully")
                .data(taskService.getMyTasks(projectId,sortBy, order))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("/{taskId}/status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTaskStatus(
            @PathVariable String taskId,
            @RequestBody @Valid TaskUpdateStatus request) {
        if (request == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Task status cannot be null", "task-06-f-03");
        }
        ApiResponse<TaskResponse> apiResponse = ApiResponse.<TaskResponse>builder()
                .code("task-06")
                .success(true)
                .message("Update task status successfully")
                .data(taskService.updateTaskStatus(taskId, request.getStatus()))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@PathVariable String taskId) {
        ApiResponse<TaskResponse> apiResponse = ApiResponse.<TaskResponse>builder()
                .code("task-07")
                .success(true)
                .message("Get task successfully")
                .data(taskService.getTaskById(taskId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/{taskId}/subtasks")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<TaskResponse>> createSubtask(
            @PathVariable String taskId,
            @RequestBody @Valid TaskRequest request) {
        ApiResponse<TaskResponse> apiResponse = ApiResponse.<TaskResponse>builder()
                .code("task-08")
                .success(true)
                .message("Create subtask successfully")
                .data(taskService.createSubtask(taskId, request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/{taskId}/dependencies/{dependencyId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<TaskResponse>> addDependency(
            @PathVariable String taskId,
            @PathVariable String dependencyId) {
        ApiResponse<TaskResponse> apiResponse = ApiResponse.<TaskResponse>builder()
                .code("task-09")
                .success(true)
                .message("Add dependency successfully")
                .data(taskService.addDependency(taskId, dependencyId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{taskId}/dependencies/{dependencyId}")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.MANAGER)
    public ResponseEntity<ApiResponse<TaskResponse>> removeDependency(
            @PathVariable String taskId,
            @PathVariable String dependencyId) {
        ApiResponse<TaskResponse> apiResponse = ApiResponse.<TaskResponse>builder()
                .code("task-10")
                .success(true)
                .message("Remove dependency successfully")
                .data(taskService.removeDependency(taskId, dependencyId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/{taskId}/comments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CommentResponse>> addComment(
            @PathVariable String taskId,
            @RequestBody @Valid CommentRequest request) {
        ApiResponse<CommentResponse> apiResponse = ApiResponse.<CommentResponse>builder()
                .code("task-11")
                .success(true)
                .message("Add comment successfully")
                .data(taskService.addComment(taskId, request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{taskId}/comments/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @PathVariable String taskId,
            @PathVariable String commentId,
            @RequestBody @Valid CommentRequest request) {
        ApiResponse<CommentResponse> apiResponse = ApiResponse.<CommentResponse>builder()
                .code("task-12")
                .success(true)
                .message("Update comment successfully")
                .data(taskService.updateComment(taskId, commentId, request))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{taskId}/comments/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable String taskId,
            @PathVariable String commentId) {
        taskService.deleteComment(taskId, commentId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("task-13")
                .success(true)
                .message("Delete comment successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("/{taskId}/assignee")
    @PreAuthorize("isAuthenticated()")
    @CheckPermission(moduleCode = ModuleCode.PROJECT, moduleRole = ModuleRole.USER)
    public ResponseEntity<ApiResponse<Void>> assigneeUpdateTask(
            @PathVariable String taskId,
            @RequestBody AssigneeUpdateTaskRequest request) {
        taskService.assigneeUpdateTask(taskId, request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("task-14")
                .success(true)
                .message("Assignee Update Task successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}