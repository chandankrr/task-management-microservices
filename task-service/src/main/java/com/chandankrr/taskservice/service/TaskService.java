package com.chandankrr.taskservice.service;

import com.chandankrr.taskservice.dto.TaskDto;
import com.chandankrr.taskservice.entity.Task;
import com.chandankrr.taskservice.entity.TaskStatus;

import java.util.List;

public interface TaskService {

    Task getTaskById(Long id) throws Exception;

    TaskDto createTask(Task task, String requesterRole) throws Exception;

    List<TaskDto> getAllTasks(TaskStatus status);

    TaskDto updateTask(Long id, Task updatedTask, Long userId) throws Exception;

    void deleteTask(Long id) throws Exception;

    TaskDto assignedToUser(Long userId, Long taskId) throws Exception;

    List<TaskDto> assignedUsersTask(Long userId, TaskStatus status);

    TaskDto completeTask(Long taskId) throws Exception;
}
