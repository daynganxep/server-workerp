package com.workerp.common_lib.dto.company_app_service.reponse;

import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import lombok.Data;

@Data
public class CompanyAppCreateModuleResponse {
    private String id;
    private ModuleCode code;
    private String name;
    private String description;
    private Boolean active;
    private String iconUrl;
}
