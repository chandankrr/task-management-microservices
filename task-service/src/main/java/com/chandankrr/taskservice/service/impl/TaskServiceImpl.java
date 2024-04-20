package com.chandankrr.taskservice.service.impl;

import com.chandankrr.taskservice.dto.TaskDto;
import com.chandankrr.taskservice.entity.Task;
import com.chandankrr.taskservice.entity.TaskStatus;
import com.chandankrr.taskservice.repository.TaskRepository;
import com.chandankrr.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepository.findById(id).orElseThrow(() -> new Exception("Task not found with id: " + id));
    }

    @Override
    public TaskDto createTask(Task task, String requesterRole) throws Exception {
        if (!requesterRole.equals("ROLE_ADMIN")) {
            throw new Exception("Only admin can create task");
        }

        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());

        Task createdTask = taskRepository.save(task);
        return modelMapper.map(createdTask, TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllTasks(TaskStatus status) {
        List<Task> allTask = taskRepository.findAll();

        return allTask.stream().filter(
                task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString())
        ).map(task -> modelMapper.map(task, TaskDto.class)).toList();
    }

    @Override
    public TaskDto updateTask(Long id, Task updatedTask, Long userId) throws Exception {
        Task existingTask = getTaskById(id);

        if (updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }

        if (updatedTask.getImage() != null) {
            existingTask.setImage(updatedTask.getImage());
        }

        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }

        if (updatedTask.getStatus() != null) {
            existingTask.setStatus(updatedTask.getStatus());
        }

        if (updatedTask.getDeadLine() != null) {
            existingTask.setDeadLine(updatedTask.getDeadLine());
        }

        Task saveTask = taskRepository.save(existingTask);
        return modelMapper.map(saveTask, TaskDto.class);
    }

    @Override
    public void deleteTask(Long id) throws Exception {
        Task task = getTaskById(id);

        taskRepository.delete(task);
    }

    @Override
    public TaskDto assignedToUser(Long userId, Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.ASSIGNED);

        Task savedTask = taskRepository.save(task);
        return modelMapper.map(savedTask, TaskDto.class);
    }

    @Override
    public List<TaskDto> assignedUsersTask(Long userId, TaskStatus status) {
        List<Task> allTask = taskRepository.findByAssignedUserId(userId);

        return allTask.stream().filter(
                task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString())
        ).map(task -> modelMapper.map(task, TaskDto.class)).toList();
    }

    @Override
    public TaskDto completeTask(Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);

        Task saveTask = taskRepository.save(task);
        return modelMapper.map(saveTask, TaskDto.class);
    }
}
