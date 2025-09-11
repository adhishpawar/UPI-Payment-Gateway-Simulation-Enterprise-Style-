package com.upi.bankservice.exception;

public class IdempotencyException extends RuntimeException {
    public IdempotencyException(String message) { super(message); }
}
