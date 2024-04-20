package com.chandankrr.submissionservice.service.impl;

import com.chandankrr.submissionservice.dto.SubmissionDto;
import com.chandankrr.submissionservice.dto.TaskDto;
import com.chandankrr.submissionservice.entity.Submission;
import com.chandankrr.submissionservice.repository.SubmissionRepository;
import com.chandankrr.submissionservice.service.SubmissionService;
import com.chandankrr.submissionservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private static final Logger log = LoggerFactory.getLogger(SubmissionServiceImpl.class);
    private final SubmissionRepository submissionRepository;
    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @Override
    public Submission getTaskSubmissionById(Long submissionId) throws Exception {
        return submissionRepository.findById(submissionId).orElseThrow(() ->
                new Exception("Task submission not found with id: " + submissionId));
    }

    @Override
    public SubmissionDto submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception {
        TaskDto task = taskService.getTaskById(taskId, jwt);
        if (task != null) {
            Submission submission = Submission.builder()
                    .taskId(taskId)
                    .userId(userId)
                    .githubLink(githubLink)
                    .status("PENDING")
                    .submissionTime(LocalDateTime.now())
                    .build();
            Submission savedSubmission = submissionRepository.save(submission);
            return modelMapper.map(savedSubmission, SubmissionDto.class);
        }
        throw new Exception("Task not found with id: " + taskId);
    }

    @Override
    public List<SubmissionDto> getAllTaskSubmissions() {
        List<Submission> submissions = submissionRepository.findAll();
        List<SubmissionDto> submissionsDto = new ArrayList<>();

        for (Submission submission : submissions) {
            submissionsDto.add(modelMapper.map(submission, SubmissionDto.class));
        }
        return submissionsDto;
    }

    @Override
    public List<SubmissionDto> getTaskSubmissionsByTaskId(Long taskId) {
        List<Submission> submissions = submissionRepository.findByTaskId(taskId);
        List<SubmissionDto> submissionsDto  = new ArrayList<>();

        for (Submission submission : submissions) {
            submissionsDto.add(modelMapper.map(submission, SubmissionDto.class));
        }
        return submissionsDto;
    }

    @Override
    public SubmissionDto acceptDeclineSubmission(Long id, String status) throws Exception {
        Submission submission = getTaskSubmissionById(id);
        submission.setStatus(status);
        if (status.equals("ACCEPT")) {
            taskService.completeTask(submission.getTaskId());
        }

        Submission saveSubmission = submissionRepository.save(submission);
        return modelMapper.map(saveSubmission, SubmissionDto.class);
    }
}
