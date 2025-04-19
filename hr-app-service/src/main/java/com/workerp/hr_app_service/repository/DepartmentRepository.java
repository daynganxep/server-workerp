package com.workerp.hr_app_service.repository;

import com.workerp.hr_app_service.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {
    List<Department> findByCompanyId(String companyId);
}
