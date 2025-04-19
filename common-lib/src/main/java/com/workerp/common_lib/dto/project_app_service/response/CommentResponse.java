package com.workerp.common_lib.dto.project_app_service.response;

import lombok.Data;

import java.util.Date;

@Data
public class CommentResponse {
    private String id;
    private String content;
    private String createdBy;
    private Date createdAt;
}