package com.workerp.company_app_service.service;

import com.workerp.common_lib.dto.company_app_service.message.*;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import com.workerp.common_lib.exception.AppException;
import com.workerp.company_app_service.mapper.CompanyModuleRoleMapper;
import com.workerp.company_app_service.model.Company;
import com.workerp.company_app_service.model.CompanyModuleRole;
import com.workerp.company_app_service.model.Module;
import com.workerp.company_app_service.repository.CompanyModuleRoleRepository;
import com.workerp.company_app_service.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyModuleRoleService {
    private final CompanyModuleRoleRepository companyModuleRoleRepository;
    private final CompanyModuleRoleMapper companyModuleRoleMapper;
    private final CompanyRepository companyRepository;

    public void companyAddOwner(CompanyAddOwnerMessage message, List<Module> modules) {
        for (Module module : modules) {
            if (companyModuleRoleRepository.existsByUserIdAndCompanyIdAndModuleId(message.getUserId(), message.getCompanyId(), module.getId())) {
                continue;
            }
            CompanyModuleRole companyModuleRole = CompanyModuleRole.builder()
                    .companyId(message.getCompanyId())
                    .userId(message.getUserId())
                    .moduleId(module.getId())
                    .role(ModuleRole.MANAGER)
                    .active(true)
                    .build();
            companyModuleRoleRepository.save(companyModuleRole);
        }
        log.info("Owner {} added to company: {}", message.getUserId(), message.getCompanyId());
    }

    public void companyAddUser(CompanyAddUserMessage message) {
        Company company = companyRepository.findById(message.getCompanyId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Company not found", "company-cmr-02-01"));
        for (Module module : company.getModules()) {
            if (companyModuleRoleRepository.existsByUserIdAndCompanyIdAndModuleId(message.getUserId(), company.getId(), module.getId())) {
                continue;
            }
            CompanyModuleRole companyModuleRole = CompanyModuleRole.builder()
                    .companyId(company.getId())
                    .userId(message.getUserId())
                    .moduleId(module.getId())
                    .role(ModuleRole.USER)
                    .active(true)
                    .build();
            companyModuleRoleRepository.save(companyModuleRole);
        }
        log.info("User {} added to company: {}", message.getUserId(), message.getCompanyId());
    }

    public void companyRemoveUser(CompanyRemoveUserMessage message) {
        Company company = companyRepository.findById(message.getCompanyId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Company not found", "company-cmr-03-01"));
        for (Module module : company.getModules()) {
            companyModuleRoleRepository.deleteByUserIdAndCompanyIdAndModuleId(message.getUserId(), company.getId(), module.getId());
        }
        log.info("User {} removed to company: {}", message.getUserId(), message.getCompanyId());
    }

    public void companyAddModule(CompanyAddModuleMessage message) {

        System.out.println("CompanyModuleRoleService.companyAddModule");
    }

    public void companyRemoveModule(CompanyRemoveModuleMessage message) {
        System.out.println("CompanyModuleRoleService.companyRemoveModule");
    }
}
