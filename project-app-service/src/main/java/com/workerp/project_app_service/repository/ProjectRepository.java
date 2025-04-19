package com.workerp.project_app_service.repository;

import com.workerp.project_app_service.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByCompanyId(String companyId);
    List<Project> findByMembersEmployeeId(String employeeId);
}