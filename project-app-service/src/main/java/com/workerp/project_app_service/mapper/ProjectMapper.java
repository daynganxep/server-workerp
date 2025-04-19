package com.workerp.project_app_service.mapper;

import com.workerp.common_lib.dto.project_app_service.request.ProjectRequest;
import com.workerp.common_lib.dto.project_app_service.response.ProjectResponse;
import com.workerp.project_app_service.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectResponse toProjectResponse(Project project);
    Project toProject(ProjectRequest request);
    List<ProjectResponse> toProjectResponseList(List<Project> projects);
}