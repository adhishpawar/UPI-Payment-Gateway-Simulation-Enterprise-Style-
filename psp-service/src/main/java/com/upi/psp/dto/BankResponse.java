package com.upi.psp.dto;


import lombok.Data;

@Data
public class BankResponse {
    private String bankId;
    private String name;
    private String upiHandle; // e.g. @axis, @icici
}