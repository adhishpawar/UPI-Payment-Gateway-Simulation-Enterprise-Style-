package com.upi.psp.dto;

import lombok.Data;

@Data
public class PaymentInitRequest {
    private String txId;
    private String payerVpa;
    private String payeeVpa;
    private double amount;
}
