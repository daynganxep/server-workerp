package com.workerp.company_app_service.service;

import com.workerp.common_lib.service.BaseRedisService;
import com.workerp.common_lib.util.Constant;
import com.workerp.company_app_service.model.CompanyModuleRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyModuleRoleEventListener extends AbstractMongoEventListener<CompanyModuleRole> {
    private final BaseRedisService redisService;

    @Override
    public void onAfterSave(AfterSaveEvent<CompanyModuleRole> event) {
        CompanyModuleRole companyModuleRole = event.getSource();
        syncToRedis(companyModuleRole);
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<CompanyModuleRole> event) {
        Document document = event.getSource();
        deleteFromRedis(document);
    }

    private void syncToRedis(CompanyModuleRole companyModuleRole) {
        String companyId = companyModuleRole.getCompanyId();
        String moduleCode = companyModuleRole.getModuleCode().toString();
        String userId = companyModuleRole.getUserId();
        String moduleRole = companyModuleRole.getModuleRole().toString();
        String key = Constant.COMPANY_MODULE_ROLE_KEY(companyId, moduleCode, userId);
        redisService.getRedisTemplate().opsForValue().set(key, moduleRole);
        log.info("Synced to Redis: key={}, value={}", key, moduleRole);
    }

    private void deleteFromRedis(Document document) {
        String companyId = document.getString("companyId");
        String moduleCode = document.getString("moduleCode");
        String userId = document.getString("userId");
        String key = Constant.COMPANY_MODULE_ROLE_KEY(companyId, moduleCode, userId);
        redisService.getRedisTemplate().delete(key);
        log.info("Deleted from Redis: key={}", key);
    }
}