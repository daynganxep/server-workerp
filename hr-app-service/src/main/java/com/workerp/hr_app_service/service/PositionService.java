package com.workerp.hr_app_service.service;

import com.workerp.common_lib.dto.hr_app_service.request.PositionRequest;
import com.workerp.common_lib.dto.hr_app_service.response.PositionResponse;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.util.SecurityUtil;
import com.workerp.hr_app_service.mapper.PositionMapper;
import com.workerp.hr_app_service.model.Position;
import com.workerp.hr_app_service.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    public PositionResponse createPosition(PositionRequest request) {
        Position position = positionMapper.toPosition(request);
        position.setCompanyId(SecurityUtil.getCompanyId());
        positionRepository.save(position);
        return positionMapper.toPositionResponse(position);
    }

    public PositionResponse updatePosition(String positionId, PositionRequest request) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Position not found", "hr-app-p-01"));
        position.setName(request.getName());
        position.setDescription(request.getDescription());
        positionRepository.save(position);
        return positionMapper.toPositionResponse(position);
    }

    public void deletePosition(String positionId) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Position not found", "hr-app-p-01"));
        positionRepository.delete(position);
    }

    public List<PositionResponse> getAllByCompanyId(String companyId) {
        return positionMapper.toPositionResponseList(positionRepository.findByCompanyId(companyId));
    }
}