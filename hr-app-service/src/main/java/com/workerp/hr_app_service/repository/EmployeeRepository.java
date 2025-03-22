package com.workerp.hr_app_service.repository;

import com.workerp.hr_app_service.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findAllByCompanyId(String companyId);
    Boolean existsByCompanyIdAndUserId(String companyId, String userId);
    List<Employee> findAllByUserId(String userId);
}
