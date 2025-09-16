package com.upi.psp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitResponse {
    private String txId;
    private String status;
    private String message;
}
