package com.workerp.common_lib.dto.hr_app_service.request;


import com.workerp.common_lib.enums.company_app_service.ContactStatus;
import com.workerp.common_lib.enums.company_app_service.ContractType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class ContractRequest {
    @NotBlank
    private String employeeId;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    private ContractType type;
    @Min(0)
    private Double salary;
    @NotNull
    private ContactStatus status;
    @NotBlank
    private String companyId;
}
