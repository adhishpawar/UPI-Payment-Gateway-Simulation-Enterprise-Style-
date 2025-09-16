package com.upi.psp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "payment_logs")
public class PaymentLog {
    @Id
    private String logId = UUID.randomUUID().toString();

    private String txId;
    private String step;         // INITIATED, DEBIT_REQUEST, CREDIT_REQUEST, etc.
    private String details;      // JSON blob
    private Instant timestamp = Instant.now();
}