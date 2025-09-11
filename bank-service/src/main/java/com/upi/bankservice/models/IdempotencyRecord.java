package com.upi.bankservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "idempotency")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdempotencyRecord {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "tx_id", unique = true, nullable = false)
    private String txId;

    @Column(name = "result_payload", columnDefinition = "text")
    private String resultPayload;

    @Column(name = "created_at")
    private Instant createdAt;
}