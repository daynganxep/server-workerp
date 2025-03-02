package com.workerp.company_app_service.model;

import com.workerp.common_lib.enums.company_app_service.ModuleRole;
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
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cpn_company_module_roles")
public class CompanyModuleRole {
    @Id
    private String id;

    @Indexed
    @Field(name = "cmr_user_id", targetType = FieldType.OBJECT_ID)
    private String userId;

    @Field(name = "cmr_active", targetType = FieldType.BOOLEAN)
    private Boolean active;

    @Field(name = "cmr_role", targetType = FieldType.STRING)
    private ModuleRole role;

    @Field(name = "cpn_company_id", targetType = FieldType.OBJECT_ID)
    private String companyId;

    @Field(name = "sys_module_id", targetType = FieldType.OBJECT_ID)
    private String moduleId;

    @CreatedDate
    @Field(name = "created_at", targetType = FieldType.DATE_TIME)
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "updated_at", targetType = FieldType.DATE_TIME)
    private Date updatedAt;
}