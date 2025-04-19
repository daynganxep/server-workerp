package com.workerp.common_lib.dto.project_app_service.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MilestoneResponse {
    private String id;
    private String title;
    private String description;
    private String projectId;
    private Date dueDate;
    private List<String> taskIds;
    private Date createdAt;
    private Date updatedAt;
}