package com.workerp.common_lib.dto.hr_app_service.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HRAppCompanyAddOwnerRequest {
    private String userId;
    private String companyId;
}
