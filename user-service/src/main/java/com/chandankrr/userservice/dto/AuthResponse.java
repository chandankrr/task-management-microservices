package com.chandankrr.userservice.dto;

public record AuthResponse(String jwt, String message, Boolean status) {

}
