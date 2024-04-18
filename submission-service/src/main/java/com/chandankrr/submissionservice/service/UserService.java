package com.chandankrr.submissionservice.service;

import com.chandankrr.submissionservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "http://localhost:8080")
public interface UserService {

    @GetMapping("/api/users/profile")
    UserDto getUserProfile(@RequestHeader("Authorization") String jwt);
}
