package com.workerp.common_lib.dto.project_app_service.response;

import com.workerp.common_lib.enums.project_app_service.TaskPriority;
import com.workerp.common_lib.enums.project_app_service.TaskStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TaskResponse {
    private String id;
    private String title;
    private String description;
    private String projectId;
    private String parentTaskId;
    private TaskStatus status;
    private TaskPriority priority;
    private Double estimatedTime;
    private Date startDate;
    private Date dueDate;
    private List<String> assignees;
    private List<String> tags;
    private List<String> dependencies;
    private List<CommentResponse> comments;
    private String milestoneId;
    private List<TaskResponse> subTasks;
    private Date createdAt;
    private Date updatedAt;
}