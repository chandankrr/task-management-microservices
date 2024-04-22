package com.chandankrr.taskservice.service;

import com.chandankrr.taskservice.dto.TaskDto;
import com.chandankrr.taskservice.entity.Task;
import com.chandankrr.taskservice.entity.TaskStatus;
import com.chandankrr.taskservice.exception.TaskNotFoundException;
import com.chandankrr.taskservice.exception.UnauthorizedAccessException;

import java.util.List;

public interface TaskService {

    Task getTaskById(Long id) throws TaskNotFoundException;

    TaskDto createTask(Task task, String requesterRole) throws UnauthorizedAccessException;

    List<TaskDto> getAllTasks(TaskStatus status);

    TaskDto updateTask(Long id, Task updatedTask, Long userId) throws TaskNotFoundException;

    void deleteTask(Long id) throws TaskNotFoundException;

    TaskDto assignedToUser(Long userId, Long taskId) throws TaskNotFoundException;

    List<TaskDto> assignedUsersTask(Long userId, TaskStatus status);

    TaskDto completeTask(Long taskId) throws TaskNotFoundException;
}
