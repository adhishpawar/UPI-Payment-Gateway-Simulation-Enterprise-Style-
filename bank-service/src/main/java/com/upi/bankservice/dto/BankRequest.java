package com.upi.bankservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BankRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String ifscCode;

    @NotBlank
    private String upiHandle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getUpiHandle() {
        return upiHandle;
    }

    public void setUpiHandle(String upiHandle) {
        this.upiHandle = upiHandle;
    }
}
