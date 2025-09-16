package com.upi.psp.repo;

import com.upi.psp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Optional<Transaction> findByTxId(String txId);

    // for idempotency check using clientTxnId
    Optional<Transaction> findByNpciTxnId(String npciTxnId);
}
