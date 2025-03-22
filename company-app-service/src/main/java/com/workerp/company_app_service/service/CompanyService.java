package com.workerp.company_app_service.service;

import com.workerp.common_lib.dto.company_app_service.message.CompanyAddOwnerMessage;
import com.workerp.common_lib.dto.company_app_service.request.CompanyAppUpdateCompanyInforRequest;
import com.workerp.common_lib.dto.company_app_service.request.CompanyAppUpdateModules;
import com.workerp.common_lib.dto.hr_app_service.request.HRAppAddOwnerToCompanyRequest;
import com.workerp.common_lib.dto.hr_app_service.response.EmployeeResponse;
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
        List<ModuleCode> moduleCodes = request.getModuleCodes();
        if (!moduleCodes.contains(ModuleCode.COMPANY)) moduleCodes.add(ModuleCode.COMPANY);
        if (!moduleCodes.contains(ModuleCode.HR)) moduleCodes.add(ModuleCode.HR);
        List<Module> modules = moduleRepository.findAllByCodeIn(request.getModuleCodes());
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

    @Transactional
    public CompanyResponse updateModules(String companyId, CompanyAppUpdateModules request) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Company not found", "company-app-company-f-03-01"));
        List<ModuleCode> updateModuleCodes = request.getModuleCodes();
        if (!updateModuleCodes.contains(ModuleCode.COMPANY)) updateModuleCodes.add(ModuleCode.COMPANY);
        if (!updateModuleCodes.contains(ModuleCode.HR)) updateModuleCodes.add(ModuleCode.HR);
        List<ModuleCode> curModuleCodes = company.getModules().stream().map(Module::getCode).toList();
        List<ModuleCode> newModuleCodes = updateModuleCodes.stream().filter(moduleCode -> !curModuleCodes.contains(moduleCode)).toList();
        List<ModuleCode> delModuleCodes = curModuleCodes.stream().filter(moduleCode -> !updateModuleCodes.contains(moduleCode)).toList();
        companyModuleRoleService.companyUpdateModule(companyId, newModuleCodes, delModuleCodes);
        List<Module> modules = moduleRepository.findAllByCodeIn(updateModuleCodes);
        company.setModules(modules);
        companyRepository.save(company);
        return companyMapper.toCompanyResponse(company);
    }

    public List<CompanyResponse > getAllMyCompanies(){
        List<EmployeeResponse> employeeResponses = employeeServiceRestApi.getAllByUser().getData();
        List<Company> companies = companyRepository.findAllById(employeeResponses.stream().map(EmployeeResponse::getCompanyId).toList());
        return companyMapper.toCompanyResponses(companies);
    }

    public CompanyResponse updateInfo(CompanyAppUpdateCompanyInforRequest request){
        String companyId = SecurityUtil.getCompanyId();
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Company not found", "company-app-company-f-05-01"));
        companyMapper.updateCompanyFromRequest(company,request);
        companyRepository.save(company);
        return companyMapper.toCompanyResponse(company);
    }
}
