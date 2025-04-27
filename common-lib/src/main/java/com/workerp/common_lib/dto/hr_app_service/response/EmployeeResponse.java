package com.workerp.common_lib.dto.hr_app_service.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EmployeeResponse {
    private String id;
    private String name;
    private String avatar;
    private String email;
    private String phone;
    private String companyId;
    private Date dob;
    private String userId;
    private DepartmentResponse department;
    private PositionResponse position;
}
