package com.wipro.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wipro.dto.PerformanceDto;

@FeignClient("PERFORMANCE-CAPSTONE")
public interface PerformanceApiClient {

	 @GetMapping("/api/performance/{id}")
	    public PerformanceDto getPerformanceById(@PathVariable("id") Long id);
	
}
