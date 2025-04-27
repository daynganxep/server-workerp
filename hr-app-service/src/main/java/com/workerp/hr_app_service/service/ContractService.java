package com.workerp.hr_app_service.service;

import com.workerp.common_lib.dto.hr_app_service.request.ContractRequest;
import com.workerp.common_lib.dto.hr_app_service.response.ContractResponse;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.util.SecurityUtil;
import com.workerp.hr_app_service.mapper.ContractMapper;
import com.workerp.hr_app_service.model.Contract;
import com.workerp.hr_app_service.model.Employee;
import com.workerp.hr_app_service.repository.ContractRepository;
import com.workerp.hr_app_service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final EmployeeRepository employeeRepository;
    private final ContractMapper contractMapper;

    public ContractResponse createContract(ContractRequest request) {
        Contract contract = contractMapper.toContract(request);
        contract.setCompanyId(SecurityUtil.getCompanyId());
        contractRepository.save(contract);
        return contractMapper.toContractResponse(contract);
    }

    public ContractResponse updateContract(String contractId, ContractRequest request) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Contract not found", "hr_app-ctr-f-02-01"));
        contractMapper.updateContractFromRequest(request,contract);
        contractRepository.save(contract);
        return contractMapper.toContractResponse(contract);
    }

    public void deleteContract(String contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Contract not found", "hr_app-ctr-f-03-01"));
        contractRepository.delete(contract);
    }

    public List<ContractResponse> getAllByEmployeeId(String employeeId) {
        return contractMapper.toContractResponseList(contractRepository.findByEmployeeId(employeeId));
    }

    public List<ContractResponse> getMyContracts() {
        String userId = SecurityUtil.getUserId();
        String companyId = SecurityUtil.getCompanyId();
        Employee employee = employeeRepository.findByUserIdAndCompanyId(userId, companyId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Employee not found", "hr_app-ctr-f-05-01"));
        return contractMapper.toContractResponseList(contractRepository.findByEmployeeId(employee.getId()));
    }
}