package com.upi.bankservice.DTOs;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DebitResponse {
    private String transactionId;
    private String status; // SUCCESS / FAILED
    private String reason;
}
