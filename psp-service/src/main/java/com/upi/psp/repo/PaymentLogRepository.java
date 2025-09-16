package com.upi.psp.repo;
import com.upi.psp.model.PaymentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentLogRepository extends JpaRepository<PaymentLog, String> {
    List<PaymentLog> findByTxIdOrderByTimestampAsc(String txId);
}
