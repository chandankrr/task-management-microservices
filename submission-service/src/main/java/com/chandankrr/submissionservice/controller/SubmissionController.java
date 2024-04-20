package com.chandankrr.submissionservice.controller;

import com.chandankrr.submissionservice.dto.SubmissionDto;
import com.chandankrr.submissionservice.dto.UserDto;
import com.chandankrr.submissionservice.entity.Submission;
import com.chandankrr.submissionservice.service.SubmissionService;
import com.chandankrr.submissionservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public SubmissionDto submitTask(@RequestParam Long taskId,
                                    @RequestParam String githubLink,
                                    @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user = userService.getUserProfile(jwt);
        return submissionService.submitTask(taskId, githubLink, user.getId(), jwt);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubmissionDto getSubmissionById(@PathVariable Long id,
                                                        @RequestHeader("Authorization") String jwt) throws Exception {
        userService.getUserProfile(jwt);
        Submission submission = submissionService.getTaskSubmissionById(id);
        return modelMapper.map(submission, SubmissionDto.class);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<SubmissionDto> getAllSubmissions(@RequestHeader("Authorization") String jwt) {
        userService.getUserProfile(jwt);
        return submissionService.getAllTaskSubmissions();
    }

    @GetMapping("/task/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public List<SubmissionDto> getAllSubmissions(@PathVariable Long taskId,
                                                              @RequestHeader("Authorization") String jwt) {
        userService.getUserProfile(jwt);
        return submissionService.getTaskSubmissionsByTaskId(taskId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubmissionDto acceptOrDeclineSubmission(@PathVariable Long id,
                                                                      @RequestParam("status") String status,
                                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        userService.getUserProfile(jwt);
        return submissionService.acceptDeclineSubmission(id, status);
    }

}
