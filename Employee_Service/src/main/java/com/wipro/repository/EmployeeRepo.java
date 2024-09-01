package com.wipro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
	
	
	
	

}
