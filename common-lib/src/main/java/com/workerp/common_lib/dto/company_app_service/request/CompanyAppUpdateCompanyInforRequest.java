package com.workerp.common_lib.dto.company_app_service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyAppUpdateCompanyInforRequest {
    @NotBlank
    private String name;
    private String domain;
    private String avatar;
    private Boolean active;
}
