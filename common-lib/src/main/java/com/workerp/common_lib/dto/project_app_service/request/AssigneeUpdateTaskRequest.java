package com.workerp.common_lib.dto.project_app_service.request;

import com.workerp.common_lib.enums.project_app_service.TaskStatus;
import lombok.Data;

import java.util.Date;

@Data
public class AssigneeUpdateTaskRequest {
    private Double estimatedTime;
    private Date dueDate;
    private TaskStatus status;
}
