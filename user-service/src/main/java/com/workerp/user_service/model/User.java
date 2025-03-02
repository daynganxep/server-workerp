package com.workerp.user_service.model;

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
@Document(collection = "usr_users")
public class User {
    @Id
    private String id;

    @Field(name = "usr_email", targetType = FieldType.STRING)
    private String email;

    @Field(name = "usr_password", targetType = FieldType.STRING)
    private String password;

    @Field(name = "usr_full_name", targetType = FieldType.STRING)
    private String fullName;

    @Field(name = "usr_avatar", targetType = FieldType.STRING)
    private String avatar;

    @Field(name = "usr_local", targetType = FieldType.BOOLEAN)
    private Boolean local;

    @Field(name = "usr_provider", targetType = FieldType.STRING)
    private String provider;

    @Field(name = "usr_provider_id", targetType = FieldType.STRING)
    private String providerId;

    @Field(name = "usr_active", targetType = FieldType.BOOLEAN)
    private Boolean active;
}