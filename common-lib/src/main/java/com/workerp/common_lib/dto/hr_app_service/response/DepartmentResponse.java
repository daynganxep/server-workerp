package com.workerp.common_lib.dto.hr_app_service.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentResponse {
    private String id;
    private String name;
    private String description;
}
