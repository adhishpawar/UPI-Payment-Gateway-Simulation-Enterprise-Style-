package com.upi.bankservice.Repositories;

import com.upi.bankservice.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, UUID> {
    Optional<Bank> findByIfscCode(String ifscCode);

    Optional<Bank> findByUpiHandle(String upiHandle);

    boolean existsByName(String name);
}
