package com.chandankrr.userservice.service;

import com.chandankrr.userservice.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUserProfile(String jwt);

    List<UserDto> getAllUsers();
}
