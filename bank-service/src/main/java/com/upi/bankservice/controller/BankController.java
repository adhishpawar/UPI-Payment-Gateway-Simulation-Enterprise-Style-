package com.upi.bankservice.controller;



import com.upi.bankservice.dto.*;
import com.upi.bankservice.models.Bank;
import com.upi.bankservice.service.BankService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/banks")
@RequiredArgsConstructor
public class BankController {

    @Autowired
    private BankService bankService;

    // POST /banks → create a new Bank    --> Bank Registration By Admin or Manager
    @PostMapping
    public ResponseEntity<ApiResponse<BankResponse>> createBank(@Valid @RequestBody BankRequest request) {
        Bank bank = new Bank();
        bank.setName(request.getName());
        bank.setIfscCode(request.getIfscCode());
        bank.setUpiHandle(request.getUpiHandle());

        Bank created = bankService.createBank(bank);

        BankResponse response = new BankResponse();
        response.setBankId(created.getBankId());
        response.setName(created.getName());
        response.setIfscCode(created.getIfscCode());
        response.setUpiHandle(created.getUpiHandle());

        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Bank created successfully", response));
    }

    // GET /banks → list all banks
    @GetMapping
    public ResponseEntity<ApiResponse<List<BankResponse>>> getAllBanks() {
        List<BankResponse> responses = bankService.getAllBanks().stream().map(b -> {
            BankResponse res = new BankResponse();
            res.setBankId(b.getBankId());
            res.setName(b.getName());
            res.setIfscCode(b.getIfscCode());
            res.setUpiHandle(b.getUpiHandle());
            return res;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Fetched all banks", responses));
    }


    // GET /banks/{id} → get bank by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BankResponse>> getBankById(@PathVariable UUID id) {
        return bankService.getBankById(id)
                .map(b -> {
                    BankResponse res = new BankResponse();
                    res.setBankId(b.getBankId());
                    res.setName(b.getName());
                    res.setIfscCode(b.getIfscCode());
                    res.setUpiHandle(b.getUpiHandle());
                    return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Bank found", res));
                })
                .orElse(ResponseEntity.ok(new ApiResponse<>("ERROR", "Bank not found", null)));
    }
}
