package com.workerp.common_lib.dto.project_app_service.request;

import com.workerp.common_lib.enums.project_app_service.ProjectStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectRequest {
    private String name;
    private String description;
    private String companyId;
    private ProjectStatus status;
    private Date startDate;
    private Date endDate;
    private List<String> tags;
}