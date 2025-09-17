package com.upi.psp.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BankAccountResponse {
    private String accountId;
    private UUID userId;
    private String bankId;
    private String accountNumber;
    private String ifscCode;
    private double balance;
    private boolean primary;
}