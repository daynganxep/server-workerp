package com.workerp.common_lib.dto.hr_app_service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class HRAppEmployeeUpdateRequest {
    @NotBlank
    private String name;
    private Date dob;
    private String avatar;
    private String email;
    private String phone;
    private String departmentId;
    private String positionId;
}
