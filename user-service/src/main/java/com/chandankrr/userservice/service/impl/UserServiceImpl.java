package com.chandankrr.userservice.service.impl;

import com.chandankrr.userservice.config.JwtProvider;
import com.chandankrr.userservice.dto.UserDto;
import com.chandankrr.userservice.entity.User;
import com.chandankrr.userservice.repository.UserRepository;
import com.chandankrr.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto getUserProfile(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> usersDto = new ArrayList<>();

        for (User user : users) {
            usersDto.add(modelMapper.map(user, UserDto.class));
        }
        return usersDto;
    }

}
