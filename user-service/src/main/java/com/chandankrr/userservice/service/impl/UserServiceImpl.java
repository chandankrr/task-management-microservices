package com.chandankrr.userservice.service.impl;

import com.chandankrr.userservice.config.JwtProvider;
import com.chandankrr.userservice.dto.UserResponse;
import com.chandankrr.userservice.entity.User;
import com.chandankrr.userservice.repository.UserRepository;
import com.chandankrr.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getUserProfile(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);

        return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getPassword(), user.getRole());
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole())).toList();
    }

}
