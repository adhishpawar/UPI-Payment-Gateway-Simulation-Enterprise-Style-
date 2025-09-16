package com.upi.psp.service;

import com.upi.psp.model.PaymentLog;
import com.upi.psp.repo.PaymentLogRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentLogService {

    private final PaymentLogRepository logRepository;

    public PaymentLogService(PaymentLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void logStep(String txId, String step, String details) {
        PaymentLog log = new PaymentLog();
        log.setTxId(txId);
        log.setStep(step);
        log.setDetails(details);
        log.setTimestamp(Instant.now());
        logRepository.save(log);
    }
}
