package com.workerp.common_lib.dto.project_app_service.request;

import com.workerp.common_lib.enums.project_app_service.ProjectMemberRole;
import lombok.Data;

@Data
public class ProjectMemberRequest {
    private String employeeId;
    private ProjectMemberRole role;
}