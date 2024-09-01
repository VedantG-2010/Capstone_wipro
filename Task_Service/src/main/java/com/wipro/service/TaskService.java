package com.wipro.service;

import java.util.List;
import com.wipro.dto.TaskDto;

public interface TaskService {

    TaskDto createTask(TaskDto taskDto);

    List<TaskDto> getAllTasks();

    TaskDto getTaskById(Long id);

    TaskDto updateTask(Long id, TaskDto taskDto);

    String deleteTask(Long id);
}
