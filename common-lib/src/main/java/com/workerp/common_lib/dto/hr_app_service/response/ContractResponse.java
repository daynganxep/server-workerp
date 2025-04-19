package com.workerp.common_lib.dto.hr_app_service.response;

import com.workerp.common_lib.enums.company_app_service.ContactStatus;
import com.workerp.common_lib.enums.company_app_service.ContractType;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ContractResponse {
    private String id;
    private String employeeId;
    private Date startDate;
    private Date endDate;
    private ContractType type;
    private Double salary;
    private ContactStatus status;
    private String companyId;
}
