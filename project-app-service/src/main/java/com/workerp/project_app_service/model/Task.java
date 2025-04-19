package com.workerp.project_app_service.model;

import com.workerp.common_lib.enums.project_app_service.TaskPriority;
import com.workerp.common_lib.enums.project_app_service.TaskStatus;
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
@Document(collection = "prj_tasks")
public class Task {
    @Id
    private String id;

    @Field(name = "tsk_title", targetType = FieldType.STRING)
    private String title;

    @Field(name = "tsk_description", targetType = FieldType.STRING)
    private String description;

    @Field(name = "tsk_project_id", targetType = FieldType.OBJECT_ID)
    private String projectId;

    @Field(name = "tsk_parent_task_id", targetType = FieldType.OBJECT_ID)
    private String parentTaskId;

    @Field(name = "tsk_status", targetType = FieldType.STRING)
    private TaskStatus status;

    @Field(name = "tsk_priority", targetType = FieldType.STRING)
    private TaskPriority priority;

    @Field(name = "tsk_estimated_time", targetType = FieldType.DOUBLE)
    private Double estimatedTime;

    @Field(name = "tsk_start_date", targetType = FieldType.DATE_TIME)
    private Date startDate;

    @Field(name = "tsk_due_date", targetType = FieldType.DATE_TIME)
    private Date dueDate;

    @Field(name = "tsk_assignees")
    private List<String> assignees;

    @Field(name = "tsk_tags")
    private List<String> tags;

    @Field(name = "tsk_dependencies")
    private List<String> dependencies;

    @Field(name = "tsk_comments")
    private List<Comment> comments;

    @Field(name = "tsk_milestone_id", targetType = FieldType.OBJECT_ID)
    private String milestoneId;

    @CreatedDate
    @Field(name = "created_at", targetType = FieldType.DATE_TIME)
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "updated_at", targetType = FieldType.DATE_TIME)
    private Date updatedAt;
}