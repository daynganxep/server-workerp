package com.workerp.project_app_service.service;

import com.workerp.common_lib.dto.project_app_service.request.MilestoneRequest;
import com.workerp.common_lib.dto.project_app_service.response.MilestoneResponse;
import com.workerp.common_lib.exception.AppException;
import com.workerp.project_app_service.mapper.MilestoneMapper;
import com.workerp.project_app_service.model.Milestone;
import com.workerp.project_app_service.model.Task;
import com.workerp.project_app_service.repository.MilestoneRepository;
import com.workerp.project_app_service.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final TaskRepository taskRepository;
    private final MilestoneMapper milestoneMapper;

    public MilestoneResponse createMilestone(String projectId, MilestoneRequest request) {
        Milestone milestone = milestoneMapper.toMilestone(request);
        milestone.setProjectId(projectId);
        milestoneRepository.save(milestone);
        return milestoneMapper.toMilestoneResponse(milestone);
    }

    public MilestoneResponse updateMilestone(String milestoneId, MilestoneRequest request) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Milestone not found", "milestone-01"));
        milestone.setTitle(request.getTitle());
        milestone.setDescription(request.getDescription());
        milestone.setDueDate(request.getDueDate());
        milestoneRepository.save(milestone);
        return milestoneMapper.toMilestoneResponse(milestone);
    }

    public void deleteMilestone(String milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Milestone not found", "milestone-01"));
        List<Task> tasks = taskRepository.findByProjectId(milestone.getProjectId());
        tasks.forEach(task -> {
            if (milestoneId.equals(task.getMilestoneId())) {
                task.setMilestoneId(null);
                taskRepository.save(task);
            }
        });
        milestoneRepository.delete(milestone);
    }

    public List<MilestoneResponse> getMilestonesByProjectId(String projectId) {
        List<Milestone> milestones = milestoneRepository.findByProjectId(projectId);
        return milestoneMapper.toMilestoneResponseList(milestones);
    }

    public MilestoneResponse addTaskToMilestone(String milestoneId, String taskId) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Milestone not found", "milestone-01"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task not found", "milestone-02"));
        if (!milestone.getTaskIds().contains(taskId)) {
            milestone.getTaskIds().add(taskId);
            task.setMilestoneId(milestoneId);
            taskRepository.save(task);
            milestoneRepository.save(milestone);
        }
        return milestoneMapper.toMilestoneResponse(milestone);
    }

    public MilestoneResponse removeTaskFromMilestone(String milestoneId, String taskId) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Milestone not found", "milestone-01"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Task not found", "milestone-02"));
        milestone.getTaskIds().remove(taskId);
        task.setMilestoneId(null);
        taskRepository.save(task);
        milestoneRepository.save(milestone);
        return milestoneMapper.toMilestoneResponse(milestone);
    }
}