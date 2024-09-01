package com.wipro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wipro.dto.AllApiResponnseDto;
import com.wipro.dto.ApiResponseDto;
import com.wipro.dto.DepartmentDto;
import com.wipro.dto.EmployeeDto;
import com.wipro.dto.PerformanceApiResponseDto;
import com.wipro.dto.PerformanceDto;
import com.wipro.dto.TaskApiResponseDto;
import com.wipro.dto.TaskDto;
import com.wipro.entity.Employee;
import com.wipro.exception.IdNotFound;
import com.wipro.repository.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
    private EmployeeRepo repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private ApiClient apiClient;
	
	@Autowired
	private TaskApiClient taskClient;
	
	@Autowired
	private PerformanceApiClient performanceClient;
    
 /*   public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }
    
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }
    
    public Employee getEmployeeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IdNotFound("Employee with the id number "+id+" not found..."));
    }
    
    public Employee updateEmployee(Long id, Employee employee) {
        if (repository.existsById(id)) {
            employee.setId(id);
            return repository.save(employee);
        }
        return null;
    }
    
    public String deleteEmployee(Long id) {
        repository.deleteById(id);
        return "Employee is successfully deleted with the ID:_"+id;

        
    }      */
	
	
	

	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		// TODO Auto-generated method stub
		Employee employee = mapper.map(employeeDto, Employee.class);
		Employee savedEmployee =repository.save(employee);
		return mapper.map(savedEmployee, EmployeeDto.class);

		
	}

	@Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        if (!repository.existsById(id)) {
            throw new IdNotFound("Employee with ID " + id + " not found.");
        }
        Employee employee = mapper.map(employeeDto, Employee.class);
        employee.setId(id);
        Employee updatedEmployee = repository.save(employee);
        return mapper.map(updatedEmployee, EmployeeDto.class);
    }
	    
		
	

	 @Override
	    public List<EmployeeDto> getAllEmployees() {
	        List<Employee> employees = repository.findAll();
	        return employees.stream()
	                        .map(employee -> mapper.map(employee, EmployeeDto.class))
	                        .collect(Collectors.toList());
	}

	 @Override
	    public EmployeeDto getEmployeeById(Long id) {
	        Employee employee = repository.findById(id)
	            .orElseThrow(() -> new IdNotFound("Employee with the ID number " + id + " not found."));
	        return mapper.map(employee, EmployeeDto.class);
	 }

	 @Override
	 public String deleteEmployee(Long id) {
	     if (repository.existsById(id)) {
	         repository.deleteById(id);
	         return "Employee is successfully deleted with the ID: " + id;
	     } else {
	         throw new IdNotFound("Employee with ID " + id + " not found.");
	     }
	 }

	@Override
	public ApiResponseDto getEmployeeByIdAndCode(Long id) {
		// TODO Auto-generated method stub
		 Employee employee = repository.findById(id)
		            .orElseThrow(() -> new IdNotFound("Employee with the ID number " + id + " not found."));
		
		 DepartmentDto departmentDto = apiClient.getDepartmentByCode(employee.getDepartmentCode());
		 
		 EmployeeDto employeeDto = mapper.map(employee, EmployeeDto.class);
		 
		 ApiResponseDto apiResponse = new ApiResponseDto();
		 
		 apiResponse.setDepartment(departmentDto);
		 apiResponse.setEmployee(employeeDto);
		 
		return apiResponse;
	}

	@Override
	public TaskApiResponseDto getTaskAndEmployee(Long id) {
		// TODO Auto-generated method stub
		 Employee employee = repository.findById(id)
		            .orElseThrow(() -> new IdNotFound("Employee with the ID number " + id + " not found."));
		
		 TaskDto taskDto = taskClient.getTaskById(employee.getTaskId());
		 
		 EmployeeDto employeeDto = mapper.map(employee, EmployeeDto.class);
		 
		 TaskApiResponseDto taskResponseDto = new TaskApiResponseDto();
		 
		 taskResponseDto.setEmployee(employeeDto);
		 taskResponseDto.setTask(taskDto);
		
		return taskResponseDto;
	}

	@Override
	public PerformanceApiResponseDto getEmployeePerformance(Long id) {
		// TODO Auto-generated method stub
		 Employee employee = repository.findById(id)
		            .orElseThrow(() -> new IdNotFound("Employee with the ID number " + id + " not found."));
		
		 PerformanceDto performanceDto = performanceClient.getPerformanceById(employee.getPerformanceId());
		 
		 EmployeeDto employeeDto = mapper.map(employee, EmployeeDto.class);
		 
		 PerformanceApiResponseDto performanceApi = new PerformanceApiResponseDto();
		 
		 performanceApi.setEmployee(employeeDto);
		 performanceApi.setPerformance(performanceDto);
		
		return performanceApi;
	}
	
public AllApiResponnseDto getAllServices(Long id) {
		
		Employee employee = repository.findById(id)
	            .orElseThrow(() -> new IdNotFound("Employee with the ID number " + id + " not found."));
		
		DepartmentDto departmentDto = apiClient.getDepartmentByCode(employee.getDepartmentCode());
		
		TaskDto taskDto = taskClient.getTaskById(employee.getTaskId());
		
		PerformanceDto performanceDto = performanceClient.getPerformanceById(employee.getPerformanceId());
		
		EmployeeDto employeeDto = mapper.map(employee, EmployeeDto.class);
		
		AllApiResponnseDto allApi = new AllApiResponnseDto();
		
		allApi.setEmployee(employeeDto);
		allApi.setDepartment(departmentDto);
		allApi.setTask(taskDto);
		allApi.setPerformance(performanceDto);
		
		return allApi;
		
	}

}
