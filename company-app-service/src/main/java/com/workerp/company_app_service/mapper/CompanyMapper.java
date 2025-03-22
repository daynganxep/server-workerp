package com.workerp.company_app_service.mapper;

import com.workerp.common_lib.dto.company_app_service.reponse.CompanyResponse;
import com.workerp.common_lib.dto.company_app_service.request.CompanyAppUpdateCompanyInforRequest;
import com.workerp.company_app_service.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyResponse toCompanyResponse(Company company);
    List<CompanyResponse> toCompanyResponses(List<Company> companies);
    void updateCompanyFromRequest(@MappingTarget Company company, CompanyAppUpdateCompanyInforRequest companyRequest);
}
