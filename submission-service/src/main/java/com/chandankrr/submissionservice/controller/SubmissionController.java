package com.chandankrr.submissionservice.controller;

import com.chandankrr.submissionservice.dto.UserDto;
import com.chandankrr.submissionservice.entity.Submission;
import com.chandankrr.submissionservice.service.SubmissionService;
import com.chandankrr.submissionservice.service.TaskService;
import com.chandankrr.submissionservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;
    private final UserService userService;

    // TODO: convent all used submission entity in dto and also the request and response used in controller
    @PostMapping()
    public ResponseEntity<Submission> submitTask(@RequestParam Long taskId,
                                                 @RequestParam String githubLink,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user = userService.getUserProfile(jwt);
        Submission submission = submissionService.submitTask(taskId, githubLink, user.getId(), jwt);

        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long id,
                                                        @RequestHeader("Authorization") String jwt) throws Exception {
        userService.getUserProfile(jwt);
        Submission submission = submissionService.getTaskSubmissionById(id);

        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Submission>> getAllSubmissions(@RequestHeader("Authorization") String jwt) {
        userService.getUserProfile(jwt);
        List<Submission> submissions = submissionService.getAllTaskSubmissions();

        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Submission>> getAllSubmissions(@PathVariable Long taskId,
                                                              @RequestHeader("Authorization") String jwt) {
        userService.getUserProfile(jwt);
        List<Submission> submissions = submissionService.getTaskSubmissionsByTaskId(taskId);

        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> acceptOrDeclineSubmission(@PathVariable Long id,
                                                                      @RequestParam("status") String status,
                                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        userService.getUserProfile(jwt);
        Submission submission = submissionService.acceptDeclineSubmission(id, status);

        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

}
