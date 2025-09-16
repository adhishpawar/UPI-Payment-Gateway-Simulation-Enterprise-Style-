package com.upi.psp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NpciStatusUpdate {
    private String txId;
    private String status;       // SUCCESS, FAILED
    private String failureCode;  // if failed
}
