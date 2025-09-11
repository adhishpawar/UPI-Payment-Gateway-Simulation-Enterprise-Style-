package com.upi.bankservice.DTOs;

import lombok.Data;

@Data
public class BankAccountRequest {
    private String accountNumber;
    private String bankId;
}