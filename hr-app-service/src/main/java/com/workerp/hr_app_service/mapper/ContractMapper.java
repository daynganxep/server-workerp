package com.workerp.hr_app_service.mapper;

import com.workerp.common_lib.dto.hr_app_service.request.ContractRequest;
import com.workerp.common_lib.dto.hr_app_service.response.ContractResponse;
import com.workerp.hr_app_service.model.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    ContractResponse toContractResponse(Contract contract);
    Contract toContract(ContractRequest request);
    void updateContractFromRequest(ContractRequest request,@MappingTarget Contract contract);
    List<ContractResponse> toContractResponseList(List<Contract> contracts);
}
