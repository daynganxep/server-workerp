package com.workerp.hr_app_service.service;

import com.workerp.common_lib.dto.company_app_service.reponse.CompanyResponse;
import com.workerp.common_lib.dto.hr_app_service.request.HRAppAddOwnerToCompanyRequest;
import com.workerp.common_lib.dto.hr_app_service.response.EmployeeResponse;
import com.workerp.common_lib.dto.message.EmailMessage;
import com.workerp.common_lib.dto.user_service.response.UserGetByIdResponse;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.service.BaseRedisService;
import com.workerp.common_lib.util.CodeGenerator;
import com.workerp.common_lib.dto.hr_app_service.request.HRAppInviteToCompanyRequest;
import com.workerp.common_lib.util.SecurityUtil;
import com.workerp.hr_app_service.mapper.EmployeeMapper;
import com.workerp.hr_app_service.message.producer.EmailMessageProducer;
import com.workerp.hr_app_service.model.Employee;
import com.workerp.hr_app_service.repository.EmployeeRepository;
import com.workerp.hr_app_service.restapi.CompanyServiceRestAPI;
import com.workerp.hr_app_service.restapi.UserServiceRestAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CompanyServiceRestAPI companyServiceRestAPI;
    private final UserServiceRestAPI userServiceRestAPI;
    private final EmailMessageProducer emailMessageProducer;
    private final BaseRedisService redisService;
    private final EmployeeMapper employeeMapper;

    @Transactional
    public void inviteToCompany(HRAppInviteToCompanyRequest request) {
        CompanyResponse company = companyServiceRestAPI.getCompanyById(request.getCompanyId()).getData();
        UserGetByIdResponse user = userServiceRestAPI.getUserById(request.getUserId()).getData();

        String code = CodeGenerator.generateSixDigitCode();
        String key = String.format("hr-app:invite-to-company:%s", code);

        redisService.saveWithTTL(key, request, 1, TimeUnit.DAYS);

        Map<String, String> values = new HashMap<>();
        values.put("companyName", company.getName());
        values.put("userFullName", user.getFullName());
        values.put("code", code);
        emailMessageProducer.sendEmailMessage(EmailMessage.builder().to(user.getEmail()).type("INVITE_TO_COMPANY").values(values).build());
    }

    @Transactional
    public void inviteToCompanyVerify(String code) {
        String key = String.format("hr-app:invite-to-company:%s", code);
        HRAppInviteToCompanyRequest hrAppInviteToCompanyRequest = (HRAppInviteToCompanyRequest) redisService.getValue(key);
        if (hrAppInviteToCompanyRequest == null)
            throw new AppException(HttpStatus.BAD_REQUEST, "Invalid code", "hr-app-employee-f-02-01");

        String currentUserId = SecurityUtil.getUserId();
        if (!currentUserId.equals(hrAppInviteToCompanyRequest.getUserId()))
            throw new AppException(HttpStatus.FORBIDDEN, "You are not allowed to perform this action", "hr-app-employee-f-02-02");

        Employee employee = Employee.builder().companyId(hrAppInviteToCompanyRequest.getCompanyId()).userId(hrAppInviteToCompanyRequest.getUserId()).build();

        employeeRepository.save(employee);
        redisService.delete(key);
    }

    @Transactional
    public EmployeeResponse addOwnerToCompany(HRAppAddOwnerToCompanyRequest request) {
        String ownerId = SecurityUtil.getUserId();
        if (!ownerId.equals(request.getUserId()))
            throw new AppException(HttpStatus.FORBIDDEN, "You are not allowed to perform this action", "hr-app-employee-f-03-01");
        Employee employee = Employee.builder().userId(request.getUserId()).companyId(request.getCompanyId()).build();
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
    }

    public List<EmployeeResponse> getAllByCompanyId(String companyId){
        List<Employee> employees = employeeRepository.findAllByCompanyId(companyId);
        return employeeMapper.toEmployeeResponses(employees);
    }
}
