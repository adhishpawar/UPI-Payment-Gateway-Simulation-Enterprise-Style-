package com.upi.bankservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BankRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String upiHandle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpiHandle() {
        return upiHandle;
    }

    public void setUpiHandle(String upiHandle) {
        this.upiHandle = upiHandle;
    }
}
