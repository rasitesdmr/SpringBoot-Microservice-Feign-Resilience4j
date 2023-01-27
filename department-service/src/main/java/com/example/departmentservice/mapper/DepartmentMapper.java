package com.example.departmentservice.mapper;

import com.example.departmentservice.model.Department;
import com.example.departmentservice.request.DepartmentRequest;
import com.example.departmentservice.response.DepartmentResponse;
import org.mapstruct.Mapper;

@Mapper
public interface DepartmentMapper {

    Department departmentRequestToDepartment(DepartmentRequest departmentRequest);

    DepartmentResponse departmentToDepartmentResponse(Department department);

}
