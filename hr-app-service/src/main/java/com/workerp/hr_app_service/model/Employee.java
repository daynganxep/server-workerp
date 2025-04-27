package com.workerp.hr_app_service.model;

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "hr_employees")
public class Employee {
    @Id
    private String id;

    @Field(name = "epl_name", targetType = FieldType.STRING)
    private String name;

    @Field(name = "epl_avatar", targetType = FieldType.STRING)
    private String avatar;

    @Field(name = "epl_company_id", targetType = FieldType.OBJECT_ID)
    private String companyId;

    @Field(name = "epl_dob", targetType = FieldType.DATE_TIME)
    private Date dob;

    @Field(name = "epl_email", targetType = FieldType.STRING)
    private String email;

    @Field(name = "epl_phone", targetType = FieldType.STRING)
    private String phone;

    @Field(name = "epl_user_id", targetType = FieldType.OBJECT_ID)
    private String userId;

    @DocumentReference(collection = "hr_departments")
    @Field(name = "epl_department")
    private Department department;

    @DocumentReference(collection = "hr_positions")
    @Field(name = "epl_position")
    private Position position;

    @CreatedDate
    @Field(name = "created_at", targetType = FieldType.DATE_TIME)
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "updated_at", targetType = FieldType.DATE_TIME)
    private Date updatedAt;
}
