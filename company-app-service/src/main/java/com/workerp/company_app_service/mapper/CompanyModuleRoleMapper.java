package com.workerp.company_app_service.mapper;

import com.workerp.common_lib.dto.company_app_service.reponse.CompanyModuleRoleResponse;
import com.workerp.common_lib.dto.company_app_service.request.CompanyModuleRoleRequest;
import com.workerp.company_app_service.model.CompanyModuleRole;
import com.workerp.company_app_service.repository.CompanyModuleRoleRepository;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyModuleRoleMapper {
    CompanyModuleRole toCompanyModuleRole(CompanyModuleRoleRequest request);

    CompanyModuleRoleResponse toCompanyModuleResponse(CompanyModuleRole companyModuleRole);

    List<CompanyModuleRoleResponse> toCompanyModuleResponses(List<CompanyModuleRole> companyModuleRoles);
}
