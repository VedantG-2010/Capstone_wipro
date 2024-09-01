package com.wipro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.entity.Performance;

public interface PerformanceRepo extends JpaRepository<Performance, Long> {
}
