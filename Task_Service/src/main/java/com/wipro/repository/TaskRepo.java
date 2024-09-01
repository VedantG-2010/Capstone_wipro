package com.wipro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.entity.Task;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
