package com.workerp.hr_app_service.mapper;

import com.workerp.common_lib.dto.hr_app_service.response.DepartmentResponse;
import com.workerp.common_lib.dto.hr_app_service.response.EmployeeResponse;
import com.workerp.common_lib.dto.hr_app_service.response.PositionResponse;
import com.workerp.hr_app_service.model.Department;
import com.workerp.hr_app_service.model.Employee;
import com.workerp.hr_app_service.model.Position;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    DepartmentResponse toDepartmentResponse(Department department);
    PositionResponse toPositionResponse(Position position);
    EmployeeResponse toEmployeeResponse(Employee employee);
    List<EmployeeResponse> toEmployeeResponses(List<Employee> employees);
}
