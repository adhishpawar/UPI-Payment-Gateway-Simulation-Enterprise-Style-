package com.upi.bankservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {

    private String status;   // SUCCESS / ERROR
    private String message;  // info or error message
    private T data;          // actual payload

    // Constructor with all fields in order: status, message, data
    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Optional: constructor for error responses (no data)
    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }
}
