package com.upi.simulator.user_service.repo;

import com.upi.simulator.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Find user by email
    Optional<User> findByEmail(String email);

    // Find user by phone
    Optional<User> findByPhone(String phone);

    // Check existence by email
    boolean existsByEmail(String email);

    // Check existence by phone
    boolean existsByPhone(String phone);
}