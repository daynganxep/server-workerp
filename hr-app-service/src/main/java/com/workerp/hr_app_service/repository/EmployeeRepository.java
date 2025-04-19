package com.workerp.hr_app_service.repository;

import com.workerp.hr_app_service.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findAllByCompanyId(String companyId);
    Boolean existsByCompanyIdAndUserId(String companyId, String userId);
    List<Employee> findAllByUserId(String userId);
    Optional<Employee> findByUserIdAndCompanyId(String userId, String companyId);
}
