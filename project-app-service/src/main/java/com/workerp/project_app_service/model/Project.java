package com.workerp.project_app_service.model;

import com.workerp.common_lib.enums.project_app_service.ProjectStatus;
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
@Document(collection = "prj_projects")
public class Project {
    @Id
    private String id;

    @Field(name = "prj_name", targetType = FieldType.STRING)
    private String name;

    @Field(name = "prj_description", targetType = FieldType.STRING)
    private String description;

    @Field(name = "prj_company_id", targetType = FieldType.OBJECT_ID)
    private String companyId;

    @Field(name = "prj_status", targetType = FieldType.STRING)
    private ProjectStatus status;

    @Field(name = "prj_start_date", targetType = FieldType.DATE_TIME)
    private Date startDate;

    @Field(name = "prj_end_date", targetType = FieldType.DATE_TIME)
    private Date endDate;

    @Field(name = "prj_members")
    private List<ProjectMember> members;

    @Field(name = "prj_tags")
    private List<String> tags;

    @Field(name = "prj_created_by", targetType = FieldType.STRING)
    private String createdBy;

    @CreatedDate
    @Field(name = "created_at", targetType = FieldType.DATE_TIME)
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "updated_at", targetType = FieldType.DATE_TIME)
    private Date updatedAt;
}