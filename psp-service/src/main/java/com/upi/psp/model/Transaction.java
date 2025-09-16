package com.upi.psp.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
    @Id
    private String txId = UUID.randomUUID().toString();

    private String fromVpa;     // payer VPA
    private String toVpa;       // payee VPA
    private double amount;
    private String currency;    // e.g., INR
    private String status;      // INITIATED, PENDING, SUCCESS, FAILED
    private String failureCode; // e.g., INSUFFICIENT_FUNDS, ACCOUNT_CLOSED
    private String npciTxnId;   // reference from NPCI
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
}
