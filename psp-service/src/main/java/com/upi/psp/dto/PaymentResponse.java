package com.upi.psp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {
    private String txId;
    private String status;       // INITIATED, SUCCESS, FAILED
    private String message;

    public PaymentResponse(String txId, String status, String transactionInitiated) {
    }
}
