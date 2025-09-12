package com.upi.simulator.user_service.controller;

import com.upi.simulator.user_service.model.KYCStatus;
import com.upi.simulator.user_service.model.User;
import com.upi.simulator.user_service.model.UserType;
import com.upi.simulator.user_service.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    //Register a new User
    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");
        String phone = request.get("phone");
        UserType userType = UserType.valueOf(request.get("userType"));

        User user = userService.registerUser(name, email, phone, userType);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{userId}/profile")
    public ResponseEntity<User> updateUserProfile(@PathVariable String userId,
                                                  @RequestBody Map<String, String> request) {
        String name = request.get("name");
        String phone = request.get("phone");

        User updatedUser = userService.updateUserProfile(userId, name, phone);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/{userId}/kyc")
    public ResponseEntity<User> updateKYCStatus(@PathVariable String userId,
                                                @RequestBody Map<String, String> request) {
        KYCStatus kycStatus = KYCStatus.valueOf(request.get("kycStatus"));
        User updatedUser = userService.updateKYCStatus(userId, kycStatus);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
