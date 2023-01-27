package com.example.departmentservice.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepartmentResponse {

    private Long id;
    private String departmentName;
    private String departmentCode;
}
