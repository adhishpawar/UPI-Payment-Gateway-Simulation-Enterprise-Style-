package com.upi.bankservice.controller;

import com.upi.bankservice.DTOs.DebitRequest;
import com.upi.bankservice.DTOs.DebitResponse;
import com.upi.bankservice.exception.InsufficientFundsException;
import com.upi.bankservice.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bank")
public class BankController {

    private final BankService bankService;
    public BankController(BankService bankService) { this.bankService = bankService; }

    @PostMapping("/debit")
    public ResponseEntity<DebitResponse> debit(@RequestBody DebitRequest req){
        try{
            DebitResponse resp = bankService.debit(req);
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
