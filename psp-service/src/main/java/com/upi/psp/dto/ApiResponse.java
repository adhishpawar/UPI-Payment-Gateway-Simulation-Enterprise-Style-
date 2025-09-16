package com.upi.psp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;   // e.g., SUCCESS, FAILED
    private String message;
    private T data;
}