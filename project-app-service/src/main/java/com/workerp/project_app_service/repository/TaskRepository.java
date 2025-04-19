package com.workerp.project_app_service.repository;

import com.workerp.project_app_service.model.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByProjectId(String projectId);
    List<Task> findByProjectIdAndParentTaskIdIsNull(String projectId, Sort sort);
    List<Task> findByProjectIdAndAssigneesContainingAndParentTaskIdIsNull(String projectId, String employeeId, Sort sort);
    List<Task> findByParentTaskId(String parentTaskId, Sort sort);
    List<Task> findByProjectIdAndParentTaskIdIsNotNull(String projectId, Sort sort);
    List<Task> findByParentTaskIdIn(List<String> parentTaskIds, Sort sort);
}