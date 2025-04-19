package com.workerp.common_lib.dto.project_app_service.request;

import com.workerp.common_lib.enums.project_app_service.TaskPriority;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private TaskPriority priority;
    private Date startDate;
    private Date dueDate;
    private List<String> assignees;
    private List<String> tags;
}