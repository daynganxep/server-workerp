package com.workerp.company_app_service.service;

import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.dto.company_app_service.reponse.CompanyAppCreateModuleResponse;
import com.workerp.common_lib.dto.company_app_service.reponse.ModuleResponse;
import com.workerp.common_lib.dto.company_app_service.request.CompanyAppCreateModuleRequest;
import com.workerp.company_app_service.mapper.ModuleMapper;
import com.workerp.company_app_service.model.Module;
import com.workerp.company_app_service.repository.ModuleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;

    @Transactional
    public CompanyAppCreateModuleResponse createModule(CompanyAppCreateModuleRequest request) {
        ModuleCode moduleCode = request.getCode();
        if (moduleRepository.existsByCode(moduleCode)) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Module with code " + moduleCode + " already exists", "company-app-module-f-01-01");
        }
        Module module = moduleMapper.toEntity(request);
        module.setActive(true);
        moduleRepository.save(module);
        return moduleMapper.toCompanyAppCreateModuleResponse(module);
    }

    public List<ModuleResponse> getAllModules() {
        return moduleMapper.toListModuleResponse(moduleRepository.findAll());
    }

    @PostConstruct
    @Transactional
    public void initModules() {
        log.info("Initializing default modules...");
        List<ModuleCode> defaultModuleCodes = Arrays.asList(ModuleCode.values());

        for (ModuleCode moduleCode : defaultModuleCodes) {
            if (!moduleRepository.existsByCode(moduleCode)) {
                Module module = Module.builder()
                        .code(moduleCode)
                        .name(moduleCode.name())
                        .active(true)
                        .build();
                moduleRepository.save(module);
                log.info("Created module: {}", moduleCode);
            } else {
                log.info("Module {} already exists, skipping...", moduleCode);
            }
        }
        log.info("Default modules initialization completed.");
    }
}
