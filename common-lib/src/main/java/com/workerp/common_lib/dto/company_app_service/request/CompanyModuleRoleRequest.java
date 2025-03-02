package com.workerp.common_lib.dto.company_app_service.request;

import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyModuleRoleRequest {
    private String userId;
    private Boolean active;
    private ModuleRole role;
    private String companyId;
    private String moduleId;
}
