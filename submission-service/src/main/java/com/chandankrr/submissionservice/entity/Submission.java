package com.chandankrr.submissionservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_submission")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long taskId;
    private Long userId;
    private String githubLink;
    private String status = "PENDING";
    private LocalDateTime submissionTime;

}
