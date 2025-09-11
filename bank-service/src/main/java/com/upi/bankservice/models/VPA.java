package com.upi.bankservice.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vpas")
public class VPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String vpa;  // e.g. user@upi

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}