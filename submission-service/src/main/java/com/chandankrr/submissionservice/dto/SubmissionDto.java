package com.chandankrr.submissionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubmissionDto  {
    private Long id;
    private Long taskId;
    private Long userId;
    private String githubLink;
    private String status;
    private LocalDateTime submissionTime;
}