package com.workerp.common_lib.dto.project_app_service.response;

import com.workerp.common_lib.enums.project_app_service.ProjectStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectResponse {
    private String id;
    private String name;
    private String description;
    private String companyId;
    private ProjectStatus status;
    private Date startDate;
    private Date endDate;
    private List<ProjectMemberResponse> members;
    private List<String> tags;
    private String createdBy;
    private Date createdAt;
    private Date updatedAt;
}