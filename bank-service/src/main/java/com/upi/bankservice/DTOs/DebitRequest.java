package com.upi.bankservice.DTOs;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitRequest {
    private String transactionId; // external idempotency key
    private String accountNumber;
    private BigDecimal amount;
    private String currency;
    private String metadata; // optional
}
