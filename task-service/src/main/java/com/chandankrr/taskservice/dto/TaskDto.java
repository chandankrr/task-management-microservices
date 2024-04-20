package com.chandankrr.taskservice.dto;

import com.chandankrr.taskservice.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String image;
    private Long assignedUserId;
    private Set<String> tags;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime deadLine;
}
