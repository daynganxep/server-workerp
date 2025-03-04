package com.workerp.company_app_service.config;

import com.workerp.company_app_service.model.CompanyModuleRole;
import com.workerp.company_app_service.repository.CompanyModuleRoleRepository;
import com.workerp.company_app_service.service.CompanyModuleRoleEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyModuleRoleSyncRunner implements CommandLineRunner {
    private final CompanyModuleRoleRepository companyModuleRoleRepository;
    private final CompanyModuleRoleEventListener companyModuleRoleEventListener;

    @Override
    public void run(String... args) throws Exception {
        companyModuleRoleEventListener.deleteOldRole();
        List<CompanyModuleRole> roles = companyModuleRoleRepository.findAll();
        for (CompanyModuleRole role : roles) {
            companyModuleRoleEventListener.syncToRedis(role);
        }
        log.info("Synchronized all CompanyModuleRole to Redis on startup.");
    }
}