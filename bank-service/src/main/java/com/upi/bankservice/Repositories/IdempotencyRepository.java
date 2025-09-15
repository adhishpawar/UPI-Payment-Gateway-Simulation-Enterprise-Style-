package com.upi.bankservice.Repositories;

import com.upi.bankservice.models.Idempotency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IdempotencyRepository extends JpaRepository<Idempotency, UUID> {
    Optional<Idempotency> findByTxId(String txId);

    boolean existsByTxId(String txId);
}