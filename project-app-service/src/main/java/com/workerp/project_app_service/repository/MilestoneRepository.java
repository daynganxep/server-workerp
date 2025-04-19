package com.workerp.project_app_service.repository;

import com.workerp.project_app_service.model.Milestone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilestoneRepository extends MongoRepository<Milestone, String> {
    List<Milestone> findByProjectId(String projectId);
}