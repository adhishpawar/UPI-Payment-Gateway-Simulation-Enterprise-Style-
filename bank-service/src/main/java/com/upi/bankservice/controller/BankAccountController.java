package com.upi.bankservice.controller;

import com.upi.bankservice.DTOs.*;
import com.upi.bankservice.exception.InsufficientFundsException;
import com.upi.bankservice.models.*;
import com.upi.bankservice.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final UserService userService;

    @PostMapping("/link")
    public BankAccount link(@RequestBody BankAccountRequest request, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return bankAccountService.linkBankAccount(request, user);
    }

    @GetMapping
    public List<BankAccount> list(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return bankAccountService.listAccounts(user);
    }

    @PostMapping("/debit")
    public ResponseEntity<DebitResponse> debit(@RequestBody DebitRequest req){
        try{
            DebitResponse resp = bankAccountService.debit(req);
            return ResponseEntity.ok(resp);
        }catch (InsufficientFundsException e){
            DebitResponse resp = new DebitResponse(req.getTransactionId(), "FAILED", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }catch (Exception e) {
            DebitResponse resp = new DebitResponse(req.getTransactionId(), "FAILED", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

}
