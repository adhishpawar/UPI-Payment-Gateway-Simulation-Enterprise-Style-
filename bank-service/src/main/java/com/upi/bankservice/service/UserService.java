package com.upi.bankservice.service;

// UserService.java

import com.upi.bankservice.DTOs.RegisterRequest;
import com.upi.bankservice.Repositories.UserRepository;
import com.upi.bankservice.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found"));
    }
}

