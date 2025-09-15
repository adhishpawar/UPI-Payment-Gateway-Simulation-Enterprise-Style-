package com.upi.bankservice.controller;

import com.upi.bankservice.dto.*;
import com.upi.bankservice.models.BankAccount;
import com.upi.bankservice.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class BankAccountController {

    @Autowired
    private BankAccountService accountService;

    // POST /accounts → create account
    @PostMapping
    public ResponseEntity<ApiResponse<BankAccountResponse>> createAccount(
            @Valid @RequestBody BankAccountRequest request) {

        BankAccount account = new BankAccount();
        account.setUserId(request.getUserId());
        account.setBankId(request.getBankId());
        account.setAccountNumber(request.getAccountNumber());
        account.setIfscCode(request.getIfscCode());
        account.setPrimary(request.isPrimary());

        BankAccount created = accountService.createAccount(account);

        BankAccountResponse response = new BankAccountResponse();
        response.setAccountId(created.getAccountId());
        response.setUserId(created.getUserId());
        response.setBankId(created.getBankId());
        response.setAccountNumber(created.getAccountNumber());
        response.setIfscCode(created.getIfscCode());
        response.setBalance(created.getBalance());
        response.setPrimary(created.isPrimary());

        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "Bank account created successfully", response)
        );
    }

    // GET /accounts/{userId} → get all accounts for a user
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<BankAccountResponse>>> getAccountsByUser(@PathVariable UUID userId) {
        List<BankAccountResponse> responses = accountService.getAccountsByUser(userId)
                .stream()
                .map(a -> {
                    BankAccountResponse res = new BankAccountResponse();
                    res.setAccountId(a.getAccountId());
                    res.setUserId(a.getUserId());
                    res.setBankId(a.getBankId());
                    res.setAccountNumber(a.getAccountNumber());
                    res.setIfscCode(a.getIfscCode());
                    res.setBalance(a.getBalance());
                    res.setPrimary(a.isPrimary());
                    return res;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "Fetched accounts for user", responses)
        );
    }

    // PATCH /accounts/{accountNumber}/debit
    @PatchMapping("/{accountNumber}/debit")
    public ResponseEntity<ApiResponse<TransactionResponse>> debit(
            @PathVariable String accountNumber,
            @Valid @RequestBody TransactionRequest request) {

        TransactionResponse response = accountService.debit(
                request.getTxId(), accountNumber, request.getAmount()
        );

        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "Debit successful", response)
        );
    }

    // PATCH /accounts/{accountNumber}/credit
    @PatchMapping("/{accountNumber}/credit")
    public ResponseEntity<ApiResponse<TransactionResponse>> credit(
            @PathVariable String accountNumber,
            @Valid @RequestBody TransactionRequest request) {

        TransactionResponse response = accountService.credit(
                request.getTxId(), accountNumber, request.getAmount()
        );

        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "Credit successful", response)
        );
    }
}
