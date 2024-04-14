package com.chandankrr.userservice.dto;

public record UserResponse(Long id, String fullName, String email, String password, String role) {
}
