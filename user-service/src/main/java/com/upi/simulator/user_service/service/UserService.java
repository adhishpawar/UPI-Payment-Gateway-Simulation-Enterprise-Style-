package com.upi.simulator.user_service.service;

import com.upi.simulator.user_service.model.KYCStatus;
import com.upi.simulator.user_service.model.User;
import com.upi.simulator.user_service.model.UserType;
import com.upi.simulator.user_service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    //user registration
    public User registerUser(String name, String email, String phone, UserType userType) {
        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("User with email already exists");
        }

        if(userRepository.existsByPhone(phone)) {
            throw new RuntimeException("User with phone already exists");
        }

        User user = new User(name, email, phone, userType);
        user.setKycStatus(KYCStatus.PENDING);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        return userRepository.save(user);
    }

    public User updateUserProfile(String userId, String name, String phone) {
        User user = getUserById(userId);

        if(name != null && !name.isEmpty()) {
            user.setName(name);
        }

        if(phone != null && !phone.isEmpty()) {
            // Optional: Check if phone already exists
            if(userRepository.existsByPhone(phone) && !user.getPhone().equals(phone)) {
                throw new RuntimeException("Phone number already in use");
            }
            user.setPhone(phone);
        }

        user.setUpdatedAt(Instant.now());
        return userRepository.save(user);
    }


    //Fetch by id
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    //Fetch all the User
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Update KYC Status

    public User updateKYCStatus(String userid, KYCStatus kycStatus){
        User user = getUserById(userid);
        user.setKycStatus(kycStatus);
        user.setUpdatedAt(Instant.now());
        return userRepository.save(user);
    }

    //Delete User
    public void deleteUser(String userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }

}
