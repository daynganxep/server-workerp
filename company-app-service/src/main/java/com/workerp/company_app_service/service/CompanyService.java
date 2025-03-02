package com.workerp.company_app_service.service;

import com.workerp.common_lib.dto.company_app_service.message.CompanyAddOwnerMessage;
import com.workerp.common_lib.dto.hr_app_service.request.HRAppAddOwnerToCompanyRequest;
import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.util.SecurityUtil;
import com.workerp.common_lib.dto.company_app_service.reponse.CompanyResponse;
import com.workerp.common_lib.dto.company_app_service.request.CompanyAppCreateCompanyRequest;
import com.workerp.company_app_service.mapper.CompanyMapper;
import com.workerp.company_app_service.model.Company;
import com.workerp.company_app_service.model.Module;
import com.workerp.company_app_service.repository.CompanyRepository;
import com.workerp.company_app_service.repository.ModuleRepository;
import com.workerp.company_app_service.restapi.EmployeeServiceRestApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final ModuleRepository moduleRepository;
    private final CompanyModuleRoleService companyModuleRoleService;
    private final EmployeeServiceRestApi employeeServiceRestApi;

    @Transactional
    public CompanyResponse createCompany(CompanyAppCreateCompanyRequest request) {
        String owner = SecurityUtil.getUserId();
        List<Module> modules = moduleRepository.findAllById(request.getModuleIds());
        Module companyModule = moduleRepository.findByCode(ModuleCode.COMPANY).orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, "Module not found", "company-app-company-f-01-01"));
        Module hrModule = moduleRepository.findByCode(ModuleCode.HR).orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, "Module not found", "company-app-company-f-01-01"));
        if (!request.getModuleIds().contains(companyModule.getId())) modules.add(companyModule);
        if (!request.getModuleIds().contains(hrModule.getId())) modules.add(hrModule);
        Company company = Company.builder().owner(owner).name(request.getName()).domain(request.getDomain()).active(true).modules(modules).build();
        companyRepository.save(company);
        companyModuleRoleService.companyAddOwner(CompanyAddOwnerMessage.builder().companyId(company.getId()).userId(owner).build(), modules);
        employeeServiceRestApi.addOwnerToCompany(HRAppAddOwnerToCompanyRequest.builder().companyId(company.getId()).userId(owner).build());
        return companyMapper.toCompanyResponse(company);
    }

    public CompanyResponse getById(String companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, "Company not found", "company-app-company-f-02-01"));
        return companyMapper.toCompanyResponse(company);
    }
}
