package com.workerp.common_lib.dto.company_app_service.request;

import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CompanyAppCreateCompanyRequest {
    @NotBlank
    private String name;
    private String domain;
    @Size(min = 1)
    private List<ModuleCode> moduleCodes;
}
