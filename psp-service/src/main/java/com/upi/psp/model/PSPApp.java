package com.upi.psp.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "psp_apps")
public class PSPApp {
    @Id
    private String pspId = UUID.randomUUID().toString();

    private String name;         // e.g., PhonePe, Paytm
    private String upiHandle;    // e.g., @paytm, @gpay
}
