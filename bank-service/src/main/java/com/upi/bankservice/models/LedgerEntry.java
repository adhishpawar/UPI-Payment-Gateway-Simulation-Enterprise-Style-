package com.upi.bankservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ledger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerEntry {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "tx_id")
    private String txId;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "amount", precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "created_at")
    private Instant createdAt;
}