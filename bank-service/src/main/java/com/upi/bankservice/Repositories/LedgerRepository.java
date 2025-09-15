package com.upi.bankservice.Repositories;


import com.upi.bankservice.models.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, UUID> {
    Optional<Ledger> findByTxId(String txId);
}
