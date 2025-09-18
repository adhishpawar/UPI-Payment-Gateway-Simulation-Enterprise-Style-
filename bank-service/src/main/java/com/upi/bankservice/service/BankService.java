package com.upi.bankservice.service;

import com.upi.bankservice.models.Bank;
import com.upi.bankservice.Repositories.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    // Create a new Bank
    public Bank createBank(Bank bank) {
        if (bankRepository.existsByName(bank.getName())) {
            throw new IllegalArgumentException("Bank with name already exists");
        }
        return bankRepository.save(bank);
    }

    // List all Banks
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    // Get Bank by IFSC
    public Optional<Bank> getBankByIfsc(String ifscCode) {
        return bankRepository.findByIfscCode(ifscCode);
    }

    // Get Bank by UPI Handle
    public Optional<Bank> getBankByUpiHandle(String upiHandle) {
        return bankRepository.findByUpiHandle(upiHandle);
    }

    // Get Bank by ID
    public Optional<Bank> getBankById(String bankId) {
        return bankRepository.findById(bankId);
    }
}
