package com.wipro.service;

import java.util.List;

import com.wipro.dto.AllApiResponnseDto;
import com.wipro.dto.ApiResponseDto;
import com.wipro.dto.EmployeeDto;
import com.wipro.dto.PerformanceApiResponseDto;
import com.wipro.dto.PerformanceDto;
import com.wipro.dto.TaskApiResponseDto;

public interface EmployeeService{
	
	public EmployeeDto createEmployee(EmployeeDto employeeDto);
	 public List<EmployeeDto> getAllEmployees();
	 public EmployeeDto getEmployeeById(Long id);
	 public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
	 public String deleteEmployee(Long id);
	 
	 public ApiResponseDto getEmployeeByIdAndCode(Long id);

	 public TaskApiResponseDto getTaskAndEmployee(Long id);
	 
	 public PerformanceApiResponseDto getEmployeePerformance(Long id);
	 
	 public AllApiResponnseDto getAllServices(Long id);

}
