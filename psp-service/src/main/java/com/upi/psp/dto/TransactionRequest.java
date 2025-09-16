package com.upi.psp.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransactionRequest {
    private String txId;
    private BigDecimal amount;
}