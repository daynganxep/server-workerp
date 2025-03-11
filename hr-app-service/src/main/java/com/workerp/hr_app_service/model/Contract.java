package com.workerp.hr_app_service.model;

import com.workerp.common_lib.enums.company_app_service.ContactStatus;
import com.workerp.common_lib.enums.company_app_service.ContractType;
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "hr_contracts")
public class Contract {
    @Id
    private String id;

    @Field(name = "ctr_employee_id", targetType = FieldType.OBJECT_ID)
    private String employeeId;

    @Field(name = "ctr_start_date", targetType = FieldType.DATE_TIME)
    private Date startDate;

    @Field(name = "ctr_end_date", targetType = FieldType.DATE_TIME)
    private Date endDate;

    @Field(name = "ctr_type", targetType = FieldType.STRING)
    private ContractType type;

    @Field(name = "ctr_salary", targetType = FieldType.DOUBLE)
    private Double salary;

    @Field(name = "ctr_status", targetType = FieldType.STRING)
    private ContactStatus status;

    @Field(name = "ctr_company_id", targetType = FieldType.OBJECT_ID)
    private String companyId;

    @CreatedDate
    @Field(name = "created_at", targetType = FieldType.DATE_TIME)
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "updated_at", targetType = FieldType.DATE_TIME)
    private Date updatedAt;
}