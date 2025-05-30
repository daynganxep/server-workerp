package com.workerp.common_lib.dto.company_app_service.reponse;

import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import lombok.Data;

import java.util.List;

@Data
public class CompanyModuleRoleResponse {
    private String id;
    private String userId;
    private Boolean active;
    private List<ModuleRole> moduleRoles;
    private String companyId;
    private ModuleCode moduleCode;
}
