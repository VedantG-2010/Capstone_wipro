package com.wipro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.entity.Department;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
	
	public Department findByDepartmentCode(String departmentCode);
}
