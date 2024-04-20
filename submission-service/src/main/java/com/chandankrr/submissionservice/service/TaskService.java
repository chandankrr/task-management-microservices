package com.chandankrr.submissionservice.service;

import com.chandankrr.submissionservice.dto.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "task-service", url = "http://localhost:8082")
public interface TaskService {

    @GetMapping("/api/tasks/{id}")
    TaskDto getTaskById(@PathVariable Long id,
                                        @RequestHeader("Authorization") String jwt) throws Exception;

    @PutMapping("/api/tasks/{id}/complete")
    TaskDto completeTask(@PathVariable Long id) throws Exception;
}
