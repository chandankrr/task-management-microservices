package com.chandankrr.submissionservice.service.impl;

import com.chandankrr.submissionservice.dto.TaskDto;
import com.chandankrr.submissionservice.entity.Submission;
import com.chandankrr.submissionservice.repository.SubmissionRepository;
import com.chandankrr.submissionservice.service.SubmissionService;
import com.chandankrr.submissionservice.service.TaskService;
import com.chandankrr.submissionservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final TaskService taskService;

    @Override
    public Submission submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception {
        TaskDto task = taskService.getTaskById(taskId, jwt);
        if (task != null) {
            Submission submission = Submission.builder()
                    .taskId(taskId)
                    .userId(userId)
                    .githubLink(githubLink)
                    .status("PENDING")
                    .submissionTime(LocalDateTime.now())
                    .build();
            return submissionRepository.save(submission);
        }
        throw new Exception("Task not found with id: " + taskId);
    }

    @Override
    public Submission getTaskSubmissionById(Long submissionId) throws Exception {
        return submissionRepository.findById(submissionId).orElseThrow(() ->
                new Exception("Task submission not found with id: " + submissionId));
    }

    @Override
    public List<Submission> getAllTaskSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionsByTaskId(Long taskId) {
        return submissionRepository.findByTaskId(taskId);
    }

    @Override
    public Submission acceptDeclineSubmission(Long id, String status) throws Exception {
        Submission submission = getTaskSubmissionById(id);
        submission.setStatus(status);
        if (status.equals("ACCEPT")) {
            taskService.completeTask(submission.getTaskId());
        }

        return submissionRepository.save(submission);
    }
}
