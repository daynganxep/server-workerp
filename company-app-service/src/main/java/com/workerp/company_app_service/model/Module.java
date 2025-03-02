package com.workerp.company_app_service.model;

import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sys_modules")
public class Module {
    @Id
    private String id;

    @Field(name = "mdl_code", targetType = FieldType.STRING)
    private ModuleCode code;

    @Field(name = "mdl_name", targetType = FieldType.STRING)
    private String name;

    @Field(name = "mdl_description", targetType = FieldType.STRING)
    private String description;

    @Field(name = "mdl_active", targetType = FieldType.BOOLEAN)
    private Boolean active;

    @Field(name = "mdl_icon_url", targetType = FieldType.STRING)
    private String iconUrl;
}