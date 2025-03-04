package com.workerp.company_app_service.message.consumer;

import com.workerp.common_lib.dto.message.CompanyModuleRoleMessage;
import com.workerp.common_lib.util.AppConstant;
import com.workerp.company_app_service.service.CompanyModuleRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyModuleRoleConsumer {
    private final CompanyModuleRoleService companyModuleRoleService;

    @RabbitListener(queues = AppConstant.ADD_EMPLOYEE_QUEUE)
    public void receiveMessage(CompanyModuleRoleMessage message) {
        companyModuleRoleService.companyAddUser(message);
    }
}