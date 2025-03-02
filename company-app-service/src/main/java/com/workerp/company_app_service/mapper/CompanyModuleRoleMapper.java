package com.workerp.company_app_service.mapper;

import com.workerp.common_lib.dto.company_app_service.request.CompanyModuleRoleRequest;
import com.workerp.company_app_service.model.CompanyModuleRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyModuleRoleMapper {
    CompanyModuleRole toCompanyModuleRole(CompanyModuleRoleRequest request);
}
