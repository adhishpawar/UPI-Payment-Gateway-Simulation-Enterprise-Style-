package com.upi.bankservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class BankResponse {
    private UUID bankId;
    private String name;
    private String ifscCode;
    private String upiHandle;

    public UUID getBankId() {
        return bankId;
    }

    public void setBankId(UUID bankId) {
        this.bankId = bankId;
    }

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