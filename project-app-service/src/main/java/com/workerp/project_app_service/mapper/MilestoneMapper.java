package com.workerp.project_app_service.mapper;

import com.workerp.common_lib.dto.project_app_service.request.MilestoneRequest;
import com.workerp.common_lib.dto.project_app_service.response.MilestoneResponse;
import com.workerp.project_app_service.model.Milestone;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MilestoneMapper {
    MilestoneResponse toMilestoneResponse(Milestone milestone);
    Milestone toMilestone(MilestoneRequest request);
    List<MilestoneResponse> toMilestoneResponseList(List<Milestone> milestones);
}