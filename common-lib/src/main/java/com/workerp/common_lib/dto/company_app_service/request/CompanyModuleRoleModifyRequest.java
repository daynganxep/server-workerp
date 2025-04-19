package com.workerp.common_lib.dto.company_app_service.request;

import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;


@Data
public class CompanyModuleRoleModifyRequest {
    @NotBlank
    private String id;
    @NotNull
    private Boolean active;
    @NotNull
    private List<ModuleRole> moduleRoles;
}
