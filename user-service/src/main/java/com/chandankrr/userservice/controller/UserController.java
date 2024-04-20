package com.chandankrr.userservice.controller;

import com.chandankrr.userservice.dto.UserDto;
import com.chandankrr.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserProfile(@RequestHeader("Authorization") String jwt) {
        return userService.getUserProfile(jwt);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers(@RequestHeader("Authorization") String jwt) {
        return userService.getAllUsers();
    }
}
