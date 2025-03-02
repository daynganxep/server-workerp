package com.workerp.company_app_service.mapper;

import com.workerp.common_lib.dto.company_app_service.reponse.CompanyAppCreateModuleResponse;
import com.workerp.common_lib.dto.company_app_service.reponse.ModuleResponse;
import com.workerp.common_lib.dto.company_app_service.request.CompanyAppCreateModuleRequest;
import com.workerp.company_app_service.model.Module;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModuleMapper {
    Module toEntity(CompanyAppCreateModuleRequest request);
    CompanyAppCreateModuleResponse toCompanyAppCreateModuleResponse(Module module);
    ModuleResponse toModuleResponse(Module module);
    List<ModuleResponse> toListModuleResponse(List<Module> modules);
}
