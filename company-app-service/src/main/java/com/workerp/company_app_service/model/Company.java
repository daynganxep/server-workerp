package com.workerp.company_app_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cpn_companies")
public class Company {
    @Id
    private String id;

    @Field(name = "cpn_owner", targetType = FieldType.STRING)
    private String owner;

    @Field(name = "cpn_name", targetType = FieldType.STRING)
    private String name;

    @Field(name = "cpn_domain", targetType = FieldType.STRING)
    private String domain;

    @Field(name = "cpn_avatar", targetType = FieldType.STRING)
    private String avatar;

    @Field(name = "cpn_active", targetType = FieldType.BOOLEAN)
    private Boolean active;

    @DocumentReference(collection = "sys_modules")
    @Field(name = "sys_modules")
    private List<Module> modules;

    @CreatedDate
    @Field(name = "created_at", targetType = FieldType.DATE_TIME)
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "updated_at", targetType = FieldType.DATE_TIME)
    private Date updatedAt;
}