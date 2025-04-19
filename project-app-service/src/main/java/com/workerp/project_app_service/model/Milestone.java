package com.workerp.project_app_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "prj_milestones")
public class Milestone {
    @Id
    private String id;

    @Field(name = "mst_title", targetType = FieldType.STRING)
    private String title;

    @Field(name = "mst_description", targetType = FieldType.STRING)
    private String description;

    @Field(name = "mst_project_id", targetType = FieldType.OBJECT_ID)
    private String projectId;

    @Field(name = "mst_due_date", targetType = FieldType.DATE_TIME)
    private Date dueDate;

    @Field(name = "mst_task_ids")
    private List<String> taskIds;

    @CreatedDate
    @Field(name = "created_at", targetType = FieldType.DATE_TIME)
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "updated_at", targetType = FieldType.DATE_TIME)
    private Date updatedAt;
}