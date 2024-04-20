package com.chandankrr.submissionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String image;
    private Long assignedUserId;
    private Set<String> tags = new HashSet<>();
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime deadLine;
}

enum TaskStatus {
    PENDING,
    ASSIGNED,
    DONE;
}
