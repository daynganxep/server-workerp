package com.workerp.company_app_service.listener;

import com.workerp.common_lib.service.BaseRedisService;
import com.workerp.common_lib.util.AppConstant;
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

    public void deleteOldRole() {
        redisService
                .getRedisTemplate()
                .delete(redisService
                        .getRedisTemplate()
                        .keys("company-app:company-module-role:*"));
    }

    public void syncToRedis(CompanyModuleRole companyModuleRole) {
        String companyId = companyModuleRole.getCompanyId();
        Boolean active = companyModuleRole.getActive();
        String moduleCode = companyModuleRole.getModuleCode().toString();
        String userId = companyModuleRole.getUserId();
        String moduleRole = companyModuleRole.getModuleRole().toString();
        String key = AppConstant.COMPANY_MODULE_ROLE_KEY(companyId, moduleCode, userId);
        if (active) {
            redisService.getRedisTemplate().opsForValue().set(key, moduleRole);
        } else {
            redisService.getRedisTemplate().delete(key);
        }
        log.info("Synced to Redis: key={}, value={}", key, moduleRole);
    }

    public void deleteFromRedis(Document document) {
        String companyId = document.getObjectId("cpn_company_id").toString();
        String moduleCode = document.getString("sys_module_code");
        String userId = document.getObjectId("cmr_user_id").toString();
        String key = AppConstant.COMPANY_MODULE_ROLE_KEY(companyId, moduleCode, userId);
        redisService.getRedisTemplate().delete(key);
        log.info("Deleted from Redis: key={}", key);
    }
}