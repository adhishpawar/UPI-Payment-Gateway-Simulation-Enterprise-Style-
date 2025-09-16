package com.upi.psp.dto;

import java.time.Instant;

public class NpciPaymentEvent {
    private String txId;
    private String fromVpa;
    private String toVpa;
    private double amount;
    private String currency;
    private Instant timestamp;
}
