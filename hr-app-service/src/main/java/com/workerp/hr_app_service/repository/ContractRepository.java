package com.workerp.hr_app_service.repository;

import com.workerp.hr_app_service.model.Contract;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends MongoRepository<Contract, String> {
    List<Contract> findByEmployeeId(String employeeId);
}