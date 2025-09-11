package com.upi.bankservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRecord {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId; // external tx id for idempotency

    @Column(name = "from_account")
    private String fromAccount;

    @Column(name = "to_account")
    private String toAccount;

    @Column(name = "amount", precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "status")
    private String status; // INITIATED, DEBITED, CREDITED, FAILED

    @Column(name = "created_at")
    private Instant createdAt;
}
