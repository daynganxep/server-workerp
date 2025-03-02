package com.workerp.hr_app_service.repository;

import com.workerp.hr_app_service.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, String> {
}
