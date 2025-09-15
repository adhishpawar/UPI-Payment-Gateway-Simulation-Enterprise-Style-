package com.upi.bankservice.dto;

import java.math.BigDecimal;

public class TransactionResponse {

    private String txId;
    private String accountNumber;
    private BigDecimal amount;
    private String type;       // DEBIT / CREDIT
    private String status;     // SUCCESS / FAILED
    private BigDecimal balanceAfter;

    // No-argument constructor (required by Jackson)
    public TransactionResponse() {
    }

    // All-argument constructor
    public TransactionResponse(String txId, String accountNumber, BigDecimal amount,
                               String type, String status, BigDecimal balanceAfter) {
        this.txId = txId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.balanceAfter = balanceAfter;
    }

    // Getters and Setters
    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "txId='" + txId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", balanceAfter=" + balanceAfter +
                '}';
    }
}
