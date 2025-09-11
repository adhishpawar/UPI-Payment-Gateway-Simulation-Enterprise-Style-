package com.upi.bankservice.controller;


import com.upi.bankservice.DTOs.LoginRequest;
import com.upi.bankservice.DTOs.RegisterRequest;
import com.upi.bankservice.models.User;
import com.upi.bankservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        // Stub JWT
        return "JWT-TOKEN-FOR-" + request.getUsername();
    }
}
