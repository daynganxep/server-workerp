package com.workerp.common_lib.dto.project_app_service.request;

import lombok.Data;

import java.util.Date;

@Data
public class MilestoneRequest {
    private String title;
    private String description;
    private Date dueDate;
}