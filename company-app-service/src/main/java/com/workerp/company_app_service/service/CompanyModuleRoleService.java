package com.workerp.company_app_service.service;

import com.workerp.common_lib.dto.company_app_service.message.*;
import com.workerp.common_lib.dto.company_app_service.reponse.CompanyModuleRoleResponse;
import com.workerp.common_lib.dto.company_app_service.request.CompanyModuleRoleModifyRequest;
import com.workerp.common_lib.dto.hr_app_service.response.EmployeeResponse;
import com.workerp.common_lib.dto.message.CompanyModuleRoleMessage;
import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.enums.company_app_service.ModuleRole;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.util.SecurityUtil;
import com.workerp.company_app_service.mapper.CompanyModuleRoleMapper;
import com.workerp.company_app_service.model.Company;
import com.workerp.company_app_service.model.CompanyModuleRole;
import com.workerp.company_app_service.model.Module;
import com.workerp.company_app_service.repository.CompanyModuleRoleRepository;
import com.workerp.company_app_service.repository.CompanyRepository;
import com.workerp.company_app_service.restapi.EmployeeServiceRestApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyModuleRoleService {
    private final CompanyModuleRoleRepository companyModuleRoleRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeServiceRestApi employeeServiceRestApi;
    private final CompanyModuleRoleMapper companyModuleRoleMapper;

    public void companyAddOwner(CompanyAddOwnerMessage message, List<Module> modules) {
        List<ModuleRole> ownerModuleRoles = List.of(ModuleRole.MANAGER,ModuleRole.USER);
        for (Module module : modules) {
            if (companyModuleRoleRepository.existsByUserIdAndCompanyIdAndModuleCode(message.getUserId(), message.getCompanyId(), module.getCode())) {
                continue;
            }
            CompanyModuleRole companyModuleRole = CompanyModuleRole.builder().companyId(message.getCompanyId()).userId(message.getUserId()).moduleCode(module.getCode()).moduleRoles(ownerModuleRoles).active(true).build();
            companyModuleRoleRepository.save(companyModuleRole);
        }
        log.info("Owner {} added to company: {}", message.getUserId(), message.getCompanyId());
    }

    public void companyUpdateModule(String companyId, List<ModuleCode> newModuleCodes, List<ModuleCode> delModuleCodes) {
        List<EmployeeResponse> employees = employeeServiceRestApi.getEmployeesByCompanyId(companyId).getData();
        for (ModuleCode moduleCode : newModuleCodes) {
            for (EmployeeResponse employee : employees) {
                if (!companyModuleRoleRepository.existsByUserIdAndCompanyIdAndModuleCode(employee.getUserId(), companyId, moduleCode)) {
                    CompanyModuleRole companyModuleRole = CompanyModuleRole.builder()
                            .companyId(companyId)
                            .moduleCode(moduleCode)
                            .moduleRoles(List.of(ModuleRole.USER))
                            .active(false)
                            .userId(employee.getUserId())
                            .build();
                    companyModuleRoleRepository.save(companyModuleRole);
                }
            }
        }
        for (ModuleCode moduleCode : delModuleCodes) {
            if (moduleCode == ModuleCode.COMPANY || moduleCode == ModuleCode.HR) continue;
            for (EmployeeResponse employee : employees) {
                companyModuleRoleRepository.deleteByCompanyIdAndModuleCodeAndUserId(companyId, moduleCode, employee.getUserId());
            }
        }
        log.info("Modules updated from company");
    }

    public List<CompanyModuleRoleResponse> getAll() {
        String companyId = SecurityUtil.getCompanyId();
        Company company = companyRepository.findById(companyId).orElseThrow();
        List<CompanyModuleRole> companyModuleRoles = companyModuleRoleRepository.findAllByCompanyIdAndModuleCodeIn(companyId, company.getModules().stream().map(Module::getCode).toList());
        return companyModuleRoleMapper.toCompanyModuleResponses(companyModuleRoles);
    }

    public List<CompanyModuleRoleResponse> getByEmployee(String companyId) {
        String userId = SecurityUtil.getUserId();
        Company company = companyRepository.findById(companyId).orElseThrow();
        List<CompanyModuleRole> companyModuleRoles = companyModuleRoleRepository.findAllByCompanyIdAndModuleCodeInAndUserIdAndActiveIsTrue(companyId, company.getModules().stream().map(Module::getCode).toList(), userId);
        return companyModuleRoleMapper.toCompanyModuleResponses(companyModuleRoles);
    }

    public void companyAddUser(CompanyModuleRoleMessage message) {
        Company company = companyRepository.findById(message.getCompanyId()).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Company not found", "company-cmr-02-01"));
        for (Module module : company.getModules()) {
            if (companyModuleRoleRepository.existsByUserIdAndCompanyIdAndModuleCode(message.getUserId(), company.getId(), module.getCode())) {
                continue;
            }
            CompanyModuleRole companyModuleRole = CompanyModuleRole.builder().companyId(company.getId()).userId(message.getUserId()).moduleCode(module.getCode()).moduleRoles(List.of(ModuleRole.USER)).active(false).build();
            companyModuleRoleRepository.save(companyModuleRole);
        }
        log.info("User {} added to company: {}", message.getUserId(), message.getCompanyId());
    }

    public void companyRemoveUser(CompanyRemoveUserMessage message) {
        Company company = companyRepository.findById(message.getCompanyId()).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Company not found", "company-cmr-03-01"));
        for (Module module : company.getModules()) {
            companyModuleRoleRepository.deleteByUserIdAndCompanyIdAndModuleCode(message.getUserId(), company.getId(), module.getCode());
        }
        log.info("User {} removed to company: {}", message.getUserId(), message.getCompanyId());
    }

    public List<CompanyModuleRoleResponse> modifyMany(List<CompanyModuleRoleModifyRequest> companyModuleRoleModifyRequests) {
        String companyId = SecurityUtil.getCompanyId();
        Company company = companyRepository.findById(companyId).orElseThrow();
        String ownerId = company.getOwner();
        List<CompanyModuleRole> companyModuleRoles = new ArrayList<>();
        for (CompanyModuleRoleModifyRequest companyModuleRoleModifyRequest : companyModuleRoleModifyRequests) {
            Optional<CompanyModuleRole> companyModuleRoleOptional = companyModuleRoleRepository.findById(companyModuleRoleModifyRequest.getId());
            if (companyModuleRoleOptional.isEmpty()) continue;
            CompanyModuleRole companyModuleRole = companyModuleRoleOptional.get();
            if(companyModuleRole.getUserId().equals(ownerId)) continue;
            if (!companyId.equals(companyModuleRole.getCompanyId())) continue;
            companyModuleRole.setActive(companyModuleRoleModifyRequest.getActive());
            companyModuleRole.setModuleRoles(companyModuleRoleModifyRequest.getModuleRoles());
            companyModuleRoles.add(companyModuleRoleRepository.save(companyModuleRole));
        }
        return companyModuleRoleMapper.toCompanyModuleResponses(companyModuleRoles);
    }
}
