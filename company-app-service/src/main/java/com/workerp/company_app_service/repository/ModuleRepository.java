package com.workerp.company_app_service.repository;

import com.workerp.common_lib.enums.company_app_service.ModuleCode;
import com.workerp.company_app_service.model.Module;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ModuleRepository extends MongoRepository<Module, String> {
    Boolean existsByCode(ModuleCode code);
    List<Module> findAllByCodeIn(List<ModuleCode> code);
}