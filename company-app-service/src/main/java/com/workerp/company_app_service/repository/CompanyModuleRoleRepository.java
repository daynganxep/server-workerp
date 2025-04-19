package com.workerp.company_app_service.repository;

import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.company_app_service.model.CompanyModuleRole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CompanyModuleRoleRepository extends MongoRepository<CompanyModuleRole, String> {
    Boolean existsByUserIdAndCompanyIdAndModuleCode(String userId, String companyId, ModuleCode moduleCode);

    void deleteByUserIdAndCompanyIdAndModuleCode(String userId, String companyId, ModuleCode moduleCode);

    void deleteByCompanyIdAndModuleCodeAndUserId(String companyId, ModuleCode moduleCode, String userId);

    List<CompanyModuleRole> findAllByCompanyIdAndModuleCodeIn(String companyId, List<ModuleCode> moduleCodes);

    List<CompanyModuleRole> findAllByCompanyIdAndModuleCodeInAndUserIdAndActiveIsTrue(String companyId, List<ModuleCode> moduleCodes, String userId);
}