package com.workerp.hr_app_service.message.producer;

import com.workerp.common_lib.dto.message.CompanyModuleRoleMessage;
import com.workerp.common_lib.util.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyModuleRoleProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendCompanyModuleRoleMessage(CompanyModuleRoleMessage message) {
        rabbitTemplate.convertAndSend(AppConstant.ADD_EMPLOYEE_EXCHANGE, AppConstant.ADD_EMPLOYEE_ROUTING_KEY, message);
    }
}