package com.workerp.project_app_service.dto;

import com.workerp.common_lib.enums.project_app_service.TaskStatus;
import lombok.Data;

@Data
public class TaskUpdateStatus {
    TaskStatus status;
}
