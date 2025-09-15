package com.upi.bankservice.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "idempotency")
public class Idempotency {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "idempotency_id", updatable = false, nullable = false)
    private UUID idempotencyId;

    @Column(name = "tx_id", nullable = false, unique = true, length = 64)
    private String txId;

    @Lob
    @Column(name = "result", nullable = false)
    private String result; // JSON of last response

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // --- Getters and Setters ---
    public UUID getIdempotencyId() { return idempotencyId; }
    public void setIdempotencyId(UUID idempotencyId) { this.idempotencyId = idempotencyId; }

    public String getTxId() { return txId; }
    public void setTxId(String txId) { this.txId = txId; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // --- Custom Builder ---
    public static IdempotencyBuilder builder() {
        return new IdempotencyBuilder();
    }

    public static class IdempotencyBuilder {
        private String txId;
        private String result;

        public IdempotencyBuilder txId(String txId) {
            this.txId = txId;
            return this;
        }

        public IdempotencyBuilder result(String result) {
            this.result = result;
            return this;
        }

        public Idempotency build() {
            Idempotency idempotency = new Idempotency();
            idempotency.setTxId(this.txId);
            idempotency.setResult(this.result);
            return idempotency;
        }
    }
}
