package com.workerp.project_app_service.service;

import com.workerp.common_lib.dto.project_app_service.request.ProjectMemberRequest;
import com.workerp.common_lib.dto.project_app_service.request.ProjectRequest;
import com.workerp.common_lib.dto.project_app_service.response.ProjectResponse;
import com.workerp.common_lib.enums.project_app_service.ProjectMemberRole;
import com.workerp.common_lib.enums.project_app_service.ProjectStatus;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.util.SecurityUtil;
import com.workerp.project_app_service.mapper.ProjectMapper;
import com.workerp.project_app_service.model.Project;
import com.workerp.project_app_service.model.ProjectMember;
import com.workerp.project_app_service.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectResponse createProject(ProjectRequest request) {
        Project project = projectMapper.toProject(request);
        project.setStatus(ProjectStatus.OPEN);
        project.setCreatedBy(SecurityUtil.getEmployeeId());
        projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    public ProjectResponse updateProject(String projectId, ProjectRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Project not found", "project_app-prj-f-02-01"));
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setCompanyId(request.getCompanyId());
        project.setStatus(request.getStatus());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setTags(request.getTags());
        projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    public void deleteProject(String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Project not found", "project_app-prj-f-03-01"));
        projectRepository.delete(project);
    }

    public List<ProjectResponse> getProjectsByCompanyId(String companyId) {
        List<Project> projects = projectRepository.findByCompanyId(companyId);
        return projectMapper.toProjectResponseList(projects);
    }

    public ProjectResponse addMember(String projectId, ProjectMemberRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Project not found", "project_app-prj-f-05-01"));
        ProjectMember member = ProjectMember.builder()
                .employeeId(request.getEmployeeId())
                .role(request.getRole())
                .build();
        if(project.getMembers() == null) {
            project.setMembers(new ArrayList<>());
        }
        if(project.getMembers().stream().anyMatch(m -> m.getEmployeeId().equals(member.getEmployeeId()))) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Member already exists", "project_app-prj-f-05-02");
        }
        project.getMembers().add(member);
        projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    public ProjectResponse removeMember(String projectId, String employeeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Project not found", "project_app-prj-f-06-01"));
        project.getMembers().removeIf(m -> m.getEmployeeId().equals(employeeId));
        projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    public List<ProjectResponse> getMyProjects() {
        String employeeId = SecurityUtil.getEmployeeId();
        List<Project> projects = projectRepository.findByMembersEmployeeId(employeeId);
        return projectMapper.toProjectResponseList(projects);
    }

    public ProjectResponse getProjectById(String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Project not found", "project_app-prj-f-08-01"));
        return projectMapper.toProjectResponse(project);
    }

    public void updateMemberRole(String projectId, String employeeId, ProjectMemberRole role){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Project not found", "project_app-prj-f-09-01"));
        project.getMembers().forEach(projectMember -> {
            if(projectMember.getEmployeeId().equals(employeeId)) {
                projectMember.setRole(role);
            }
        });
        projectRepository.save(project);
    }
}