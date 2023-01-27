package com.example.departmentservice.service;

import com.example.departmentservice.model.Department;
import com.example.departmentservice.request.DepartmentRequest;
import com.example.departmentservice.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest departmentRequest);

   // Department createDepartment(Department department);

    Department getDepartmentById(Long id);


}
