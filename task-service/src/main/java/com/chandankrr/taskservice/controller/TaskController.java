package com.chandankrr.taskservice.controller;

import com.chandankrr.taskservice.dto.TaskDto;
import com.chandankrr.taskservice.dto.UserDto;
import com.chandankrr.taskservice.entity.Task;
import com.chandankrr.taskservice.entity.TaskStatus;
import com.chandankrr.taskservice.service.TaskService;
import com.chandankrr.taskservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getTaskById(@PathVariable Long id,
                                            @RequestHeader("Authorization") String jwt) throws Exception {
        userService.getUserProfile(jwt);
        Task task = taskService.getTaskById(id);
        return modelMapper.map(task, TaskDto.class);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody Task task,
                              @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user = userService.getUserProfile(jwt);
        return taskService.createTask(task, user.getRole());
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getAssignedUsersTask(@RequestParam(required = false) TaskStatus status,
                                                     @RequestHeader("Authorization") String jwt) {
        UserDto user = userService.getUserProfile(jwt);
        return taskService.assignedUsersTask(user.getId(), status);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getAllTask(@RequestParam(required = false) TaskStatus status,
                                                           @RequestHeader("Authorization") String jwt) {
        userService.getUserProfile(jwt);
        return taskService.getAllTasks(status);
    }

    @PutMapping("/{id}/user/{userid}/assigned")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto assignedTaskToUser(@PathVariable Long id,
                                                   @PathVariable Long userid,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        userService.getUserProfile(jwt);
        return taskService.assignedToUser(userid, id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto updateTask(@PathVariable Long id,
                                           @RequestBody Task req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user = userService.getUserProfile(jwt);
        return taskService.updateTask(id, req, user.getId());
    }

    @PutMapping("/{id}/complete")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto completeTask(@PathVariable Long id) throws Exception {
        return taskService.completeTask(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) throws Exception {
        taskService.deleteTask(id);
    }
}
