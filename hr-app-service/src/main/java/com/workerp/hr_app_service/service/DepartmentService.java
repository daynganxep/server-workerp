package com.workerp.hr_app_service.service;

import com.workerp.common_lib.dto.hr_app_service.request.DepartmentRequest;
import com.workerp.common_lib.dto.hr_app_service.response.DepartmentResponse;
import com.workerp.common_lib.exception.AppException;
import com.workerp.common_lib.util.SecurityUtil;
import com.workerp.hr_app_service.mapper.DepartmentMapper;
import com.workerp.hr_app_service.model.Department;
import com.workerp.hr_app_service.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department department = departmentMapper.toDepartment(request);
        department.setCompanyId(SecurityUtil.getCompanyId());
        departmentRepository.save(department);
        return departmentMapper.toDepartmentResponse(department);
    }

    public DepartmentResponse updateDepartment(String departmentId, DepartmentRequest request) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Department not found", "hr_app-dpm-f-02-01"));
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        departmentRepository.save(department);
        return departmentMapper.toDepartmentResponse(department);
    }

    public void deleteDepartment(String departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Department not found", "hr_app-dpm-f-03-01"));
        departmentRepository.delete(department);
    }

    public List<DepartmentResponse> getAllByCompanyId(String companyId) {
        return departmentMapper.toDepartmentResponseList(departmentRepository.findByCompanyId(companyId));
    }
}