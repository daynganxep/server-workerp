package com.workerp.project_app_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Field(name = "cmt_id", targetType = FieldType.STRING)
    private String id;

    @Field(name = "cmt_content", targetType = FieldType.STRING)
    private String content;

    @Field(name = "cmt_created_by", targetType = FieldType.STRING)
    private String createdBy;

    @Field(name = "cmt_created_at", targetType = FieldType.DATE_TIME)
    private Date createdAt;
}