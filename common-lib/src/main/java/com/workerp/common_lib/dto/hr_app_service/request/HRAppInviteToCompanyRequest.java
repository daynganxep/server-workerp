package com.workerp.common_lib.dto.hr_app_service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HRAppInviteToCompanyRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotBlank
    private String userId;
    @NotBlank
    private String companyId;
}
