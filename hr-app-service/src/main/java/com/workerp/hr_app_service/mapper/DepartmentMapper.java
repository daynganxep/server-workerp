package com.workerp.hr_app_service.mapper;

import com.workerp.common_lib.dto.hr_app_service.request.DepartmentRequest;
import com.workerp.common_lib.dto.hr_app_service.response.DepartmentResponse;
import com.workerp.hr_app_service.model.Department;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentResponse toDepartmentResponse(Department department);
    Department toDepartment(DepartmentRequest request);
    List<DepartmentResponse> toDepartmentResponseList(List<Department> departments);
}
