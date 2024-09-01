package com.wipro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description="ApiResponseDTO Model Info")
@Data @AllArgsConstructor @NoArgsConstructor
public class ApiResponseDto {
	
//	private EmployeeDto employeeDto;
//	private DepartmentDto departmentDto;

	@Schema(description = "Enter employee")
	private EmployeeDto employee;
	@Schema(description = "Enter department")
	private DepartmentDto department;
	
}
