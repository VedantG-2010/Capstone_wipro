package com.wipro.service;

import java.util.List;

import com.wipro.dto.DepartmentDto;

public interface DepartmentService {

    DepartmentDto createDepartment(DepartmentDto departmentDto);
    
    List<DepartmentDto> getAllDepartments();
    
    DepartmentDto getDepartmentById(Long id);
    
    DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto);
    
    DepartmentDto getDepartmentByCode(String code);
    
    String deleteDepartment(Long id);
}
