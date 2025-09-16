package com.upi.psp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String txId;
    private String accountNumber;
    private BigDecimal amount;
    private String type;       // DEBIT or CREDIT
    private String status;     // SUCCESS or FAILED
    private BigDecimal balanceAfter;
}

