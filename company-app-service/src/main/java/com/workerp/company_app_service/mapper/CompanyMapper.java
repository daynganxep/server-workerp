package com.workerp.company_app_service.mapper;

import com.workerp.common_lib.dto.company_app_service.reponse.CompanyResponse;
import com.workerp.company_app_service.model.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyResponse toCompanyResponse(Company company);
}
