package com.upi.psp.service;

import com.upi.psp.dto.PaymentRequest;
import com.upi.psp.dto.PaymentResponse;
import com.upi.psp.dto.NpciStatusUpdate;
import com.upi.psp.model.Transaction;
import com.upi.psp.repo.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final PaymentLogService logService;

    public TransactionService(TransactionRepository transactionRepository, PaymentLogService logService) {
        this.transactionRepository = transactionRepository;
        this.logService = logService;
    }

    @Transactional
    public PaymentResponse initiateTransaction(PaymentRequest request) {
        Transaction txn = new Transaction();
        txn.setFromVpa(request.getFromVpa());
        txn.setToVpa(request.getToVpa());
        txn.setAmount(request.getAmount());
        txn.setCurrency(request.getCurrency());
        txn.setStatus("INITIATED");
        txn.setCreatedAt(Instant.now());

        transactionRepository.save(txn);
        logService.logStep(txn.getTxId(), "INITIATED", "Transaction created");

        return new PaymentResponse(txn.getTxId(), txn.getStatus(), "Transaction initiated");
    }

    @Transactional
    public void updateTransactionStatus(NpciStatusUpdate update) {
        Optional<Transaction> txnOpt = transactionRepository.findByTxId(update.getTxId());
        if (txnOpt.isPresent()) {
            Transaction txn = txnOpt.get();
            txn.setStatus(update.getStatus());
            txn.setFailureCode(update.getFailureCode());
            txn.setUpdatedAt(Instant.now());
            transactionRepository.save(txn);

            logService.logStep(txn.getTxId(), "STATUS_UPDATE", "Updated by NPCI: " + update.getStatus());
        }
    }

    public Optional<Transaction> getTransaction(String txId) {
        return transactionRepository.findByTxId(txId);
    }
}
