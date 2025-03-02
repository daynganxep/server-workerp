package com.workerp.common_lib.dto.company_app_service.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRemoveUserMessage {
    private String userId;
    private String companyId;
}
