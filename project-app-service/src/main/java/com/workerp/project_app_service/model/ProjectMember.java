package com.workerp.project_app_service.model;

import com.workerp.common_lib.enums.project_app_service.ProjectMemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember {
    @Field(name = "pm_employee_id", targetType = FieldType.STRING)
    private String employeeId;

    @Field(name = "pm_role", targetType = FieldType.STRING)
    private ProjectMemberRole role;
}