package com.upi.psp.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VPARequest {
    private UUID userId;
    private String vpaAddress;    // optional (auto-generate if null)
    private String pspId;
    private String bankAccountId;
}
