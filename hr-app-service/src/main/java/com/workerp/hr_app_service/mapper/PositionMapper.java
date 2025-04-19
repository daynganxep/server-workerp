package com.workerp.hr_app_service.mapper;

import com.workerp.common_lib.dto.hr_app_service.request.PositionRequest;
import com.workerp.common_lib.dto.hr_app_service.response.PositionResponse;
import com.workerp.hr_app_service.model.Position;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PositionMapper {
    PositionResponse toPositionResponse(Position position);
    Position toPosition(PositionRequest request);
    List<PositionResponse> toPositionResponseList(List<Position> positions);
}