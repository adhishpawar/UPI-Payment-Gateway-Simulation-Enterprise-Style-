package com.upi.psp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayeeValidationResponse {
    private String payeeVpa;
    private boolean valid;
    private String message;
}
