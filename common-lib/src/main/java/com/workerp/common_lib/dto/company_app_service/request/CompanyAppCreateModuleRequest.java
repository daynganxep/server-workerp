package com.workerp.common_lib.dto.company_app_service.request;

import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CompanyAppCreateModuleRequest {
    @NotNull
    private ModuleCode code;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String iconUrl;
}
