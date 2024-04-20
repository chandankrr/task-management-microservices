package com.chandankrr.submissionservice.service;

import com.chandankrr.submissionservice.dto.SubmissionDto;
import com.chandankrr.submissionservice.entity.Submission;

import java.util.List;

public interface SubmissionService {

    Submission getTaskSubmissionById(Long submissionId) throws Exception;

    SubmissionDto submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception;

    List<SubmissionDto> getAllTaskSubmissions();

    List<SubmissionDto> getTaskSubmissionsByTaskId(Long taskId);

    SubmissionDto acceptDeclineSubmission(Long id, String status) throws Exception;


}
