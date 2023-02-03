package com.example.departmentservice.service;

import com.example.departmentservice.exception.DepartmentNotFoundException;
import com.example.departmentservice.mapper.DepartmentMapper;
import com.example.departmentservice.model.Department;
import com.example.departmentservice.repository.DepartmentRepository;
import com.example.departmentservice.request.DepartmentRequest;
import com.example.departmentservice.response.DepartmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

//    @Override
//    public Department createDepartment(Department department) {
//        return departmentRepository.save(department);
//    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {
        Department department = departmentMapper.departmentRequestToDepartment(departmentRequest);
        department = departmentRepository.save(department);
        return departmentMapper.departmentToDepartmentResponse(department);
    }

    @Override
    public Department getDepartmentById(Long id) {
        System.out.println("Kullanıcı bilgileri alınmaya geldi");
        return departmentRepository.findById(id).orElseThrow(()-> new DepartmentNotFoundException("Not Found"));
    }


}
