package com.workerp.common_lib.dto.company_app_service.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAddOwnerMessage {
    private String companyId;
    private String userId;
}
