package com.upi.bankservice.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ledger")
public class Ledger {

    @Id
    @GeneratedValue(generator = "uuid2")
    @org.hibernate.annotations.GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ledger_id", updatable = false, nullable = false)
    private UUID ledgerId;

    @Column(name = "tx_id", nullable = false, unique = true)
    private String txId;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "balance_after", precision = 19, scale = 2)
    private BigDecimal balanceAfter;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type; // DEBIT / CREDIT

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status; // SUCCESS / FAILED / PENDING

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // --- Enums ---
    public enum TransactionType { DEBIT, CREDIT }
    public enum TransactionStatus { SUCCESS, FAILED, PENDING }

    // --- Getters and Setters ---
    public UUID getLedgerId() { return ledgerId; }
    public void setLedgerId(UUID ledgerId) { this.ledgerId = ledgerId; }

    public String getTxId() { return txId; }
    public void setTxId(String txId) { this.txId = txId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public BigDecimal getBalanceAfter() { return balanceAfter; }
    public void setBalanceAfter(BigDecimal balanceAfter) { this.balanceAfter = balanceAfter; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    // --- Custom Builder ---
    public static class LedgerBuilder {
        private String txId;
        private String accountNumber;
        private BigDecimal amount;
        private TransactionType type;
        private TransactionStatus status;
        private BigDecimal balanceAfter;

        public LedgerBuilder txId(String txId) { this.txId = txId; return this; }
        public LedgerBuilder accountNumber(String accountNumber) { this.accountNumber = accountNumber; return this; }
        public LedgerBuilder amount(BigDecimal amount) { this.amount = amount; return this; }
        public LedgerBuilder type(TransactionType type) { this.type = type; return this; }
        public LedgerBuilder status(TransactionStatus status) { this.status = status; return this; }
        public LedgerBuilder balanceAfter(BigDecimal balanceAfter) { this.balanceAfter = balanceAfter; return this; }

        public Ledger build() {
            Ledger ledger = new Ledger();
            ledger.setTxId(this.txId);
            ledger.setAccountNumber(this.accountNumber);
            ledger.setAmount(this.amount);
            ledger.setType(this.type);
            ledger.setStatus(this.status);
            ledger.setBalanceAfter(this.balanceAfter);
            return ledger;
        }
    }

    public static LedgerBuilder builder() { return new LedgerBuilder(); }
}
