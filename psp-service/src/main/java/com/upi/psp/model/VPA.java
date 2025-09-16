package com.upi.psp.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "vpas")
public class VPA {
    @Id
    private String vpaId = UUID.randomUUID().toString();

    private UUID userId;       // maps to AuthService user
    private String vpaAddress;   // e.g. user@psp
    private String pspId;        // PSPApp reference
    private String bankAccountId;
    private Instant createdAt = Instant.now();
}

