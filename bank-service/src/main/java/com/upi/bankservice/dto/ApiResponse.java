package com.upi.bankservice.dto;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {

    private String status;   // SUCCESS / ERROR
    private String message;  // info or error message
    private T data;          // actual payload

    // Default constructor
    public ApiResponse() {
    }

    // Constructor with all fields
    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Constructor for error responses (no data)
    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
