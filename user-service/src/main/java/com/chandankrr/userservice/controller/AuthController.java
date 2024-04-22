package com.chandankrr.userservice.controller;

import com.chandankrr.userservice.config.JwtProvider;
import com.chandankrr.userservice.dto.AuthResponse;
import com.chandankrr.userservice.dto.LoginRequest;
import com.chandankrr.userservice.entity.User;
import com.chandankrr.userservice.exception.EmailAlreadyUsedException;
import com.chandankrr.userservice.exception.InvalidCredentialsException;
import com.chandankrr.userservice.exception.UserNotFoundException;
import com.chandankrr.userservice.repository.UserRepository;
import com.chandankrr.userservice.service.CustomerUserServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerUserServiceImplementation customUserDetails;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse createUserHandler(@RequestBody User user) throws EmailAlreadyUsedException {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();
        String role = user.getRole();

        User isEmailExist = userRepository.findByEmail(email);

        if (isEmailExist != null) {
            throw new EmailAlreadyUsedException("Email is already used with another account");
        }

        User createdUser = User.builder()
                .email(email)
                .fullName(fullName)
                .role(role)
                .password(passwordEncoder.encode(password))
                .build();

        userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);

        return new AuthResponse(token, "Register Success", true);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse signin(@RequestBody LoginRequest loginRequest) throws InvalidCredentialsException, UserNotFoundException {
        String username = loginRequest.email();
        String password = loginRequest.password();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);

        return new AuthResponse(token, "Login Success", true);
    }

    private Authentication authenticate(String username, String password) throws InvalidCredentialsException, UserNotFoundException {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if (userDetails == null) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
