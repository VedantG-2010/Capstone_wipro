package com.wipro.service;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.dto.DepartmentDto;
import com.wipro.entity.Department;
import com.wipro.exception.IdNotFound;
import com.wipro.repository.DepartmentRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo repository;
    
    @Autowired
    private ModelMapper mapper;

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department = mapper.map(departmentDto, Department.class);
        Department savedDepartment = repository.save(department);
        return mapper.map(savedDepartment, DepartmentDto.class);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = repository.findAll();
        return departments.stream()
                          .map(department -> mapper.map(department, DepartmentDto.class))
                          .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        Department department = repository.findById(id)
            .orElseThrow(() -> new IdNotFound("Department with ID " + id + " not found."));
        return mapper.map(department, DepartmentDto.class);
    }

    @Override
    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        if (!repository.existsById(id)) {
            throw new IdNotFound("Department with ID " + id + " not found.");
        }
        Department department = mapper.map(departmentDto, Department.class);
        department.setId(id);
        Department updatedDepartment = repository.save(department);
        return mapper.map(updatedDepartment, DepartmentDto.class);
    }

    @Override
    public String deleteDepartment(Long id) {
        if (!repository.existsById(id)) {
            throw new IdNotFound("Department with ID " + id + " not found.");
        }
        repository.deleteById(id);
        return "Department successfully deleted with ID " + id;
    }

    @Override
    public DepartmentDto getDepartmentByCode(String code) {
        Department department = repository.findByDepartmentCode(code);
        if (department == null) {
            throw new IdNotFound("Department with code " + code + " not found.");
        }
        return mapper.map(department, DepartmentDto.class);
    }
}
