package com.example.departmentservice.controller;

import com.example.departmentservice.model.Department;
import com.example.departmentservice.request.DepartmentRequest;
import com.example.departmentservice.response.DepartmentResponse;
import com.example.departmentservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/createDepartment")
    public ResponseEntity<DepartmentResponse>createDepartment(@RequestBody DepartmentRequest departmentRequest){
        return new ResponseEntity<>(departmentService.createDepartment(departmentRequest), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Department>getDepartmentById(@PathVariable ("id") Long id){
        return new ResponseEntity<>(departmentService.getDepartmentById(id), HttpStatus.OK);
    }


}
