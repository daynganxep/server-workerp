package com.workerp.company_app_service.repository;

import com.workerp.company_app_service.model.CompanyModuleRole;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyModuleRoleRepository extends MongoRepository<CompanyModuleRole, String> {
    Boolean existsByUserIdAndCompanyIdAndModuleId(String userId, String companyId, String moduleId);
    void deleteByUserIdAndCompanyIdAndModuleId(String userId, String companyId, String moduleId);
}