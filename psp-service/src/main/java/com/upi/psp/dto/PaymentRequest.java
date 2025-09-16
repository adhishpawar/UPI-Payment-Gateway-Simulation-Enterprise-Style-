package com.upi.psp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private String fromVpa;
    private String toVpa;
    private double amount;
    private String currency;
    private String clientTxnId;   // from mobile app (for idempotency)
}
