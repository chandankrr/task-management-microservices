package com.chandankrr.userservice.service;

import com.chandankrr.userservice.dto.UserResponse;

import java.util.List;

public interface UserService {

    public UserResponse getUserProfile(String jwt);

    public List<UserResponse> getAllUsers();
}
