package com.workerp.hr_app_service.restapi;

import com.workerp.common_lib.annotation.ApiGatewayClient;
import com.workerp.common_lib.dto.api.ApiResponse;
import com.workerp.common_lib.dto.company_app_service.reponse.CompanyResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ApiGatewayClient
public interface CompanyServiceRestAPI {
    @GetMapping("/api/company-app/companies/{companyId}")
    ApiResponse<CompanyResponse> getCompanyById(@PathVariable String companyId);
}
