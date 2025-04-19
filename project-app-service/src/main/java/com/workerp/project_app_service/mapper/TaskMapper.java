package com.workerp.project_app_service.mapper;

import com.workerp.common_lib.dto.project_app_service.request.AssigneeUpdateTaskRequest;
import com.workerp.common_lib.dto.project_app_service.request.TaskRequest;
import com.workerp.common_lib.dto.project_app_service.response.CommentResponse;
import com.workerp.common_lib.dto.project_app_service.response.TaskResponse;
import com.workerp.project_app_service.model.Comment;
import com.workerp.project_app_service.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponse toTaskResponse(Task task);
    Task toTask(TaskRequest request);
    List<TaskResponse> toTaskResponseList(List<Task> tasks);
    CommentResponse toCommentResponse(Comment comment);
    void assignUpdateTask(AssigneeUpdateTaskRequest request,@MappingTarget Task task);
}