package com.upi.bankservice.service;

import com.upi.bankservice.DTOs.DebitRequest;
import com.upi.bankservice.DTOs.DebitResponse;
import com.upi.bankservice.Repositories.AccountRepository;
import com.upi.bankservice.Repositories.IdempotencyRepository;
import com.upi.bankservice.Repositories.LedgerRepository;
import com.upi.bankservice.Repositories.TransactionRepository;
import com.upi.bankservice.exception.InsufficientFundsException;
import com.upi.bankservice.models.Account;
import com.upi.bankservice.models.IdempotencyRecord;
import com.upi.bankservice.models.LedgerEntry;
import com.upi.bankservice.models.TransactionRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final IdempotencyRepository idempotencyRepository;
    private final LedgerRepository ledgerRepository;

    public BankService(AccountRepository accountRepository,
                       TransactionRepository transactionRepository,
                       IdempotencyRepository idempotencyRepository,
                       LedgerRepository ledgerRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.idempotencyRepository = idempotencyRepository;
        this.ledgerRepository = ledgerRepository;
    }

    @Transactional
    public DebitResponse debit(DebitRequest req){

        // Idempotency check
        Optional<IdempotencyRecord> existing = idempotencyRepository.findByTxId(req.getTransactionId());
        if (existing.isPresent()) {
            // If already processed, return stored response (simple approach)
            return new DebitResponse(req.getTransactionId(), "SUCCESS", "Already processed");
        }

        // prepare transaction record
        TransactionRecord tx = TransactionRecord.builder()
                .id(UUID.randomUUID())
                .transactionId(req.getTransactionId())
                .fromAccount(req.getAccountNumber())
                .toAccount(null)
                .amount(req.getAmount())
                .status("INITIATED")
                .createdAt(Instant.now())
                .build();
        transactionRepository.save(tx);

        //lock the account row for update (pessimistic)
        Account account = accountRepository.findByAccountNumberForUpdate(req.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not Found: " + req.getAccountNumber()));

        BigDecimal balance = account.getBalance();
        if(balance.compareTo(req.getAmount()) <0 ){
            tx.setStatus("FAILED");
            transactionRepository.save(tx);
            throw new InsufficientFundsException("Insufficient Funds");
        }

        //debit
        account.setBalance(balance.subtract(req.getAmount()));
        account.setUpdatedAt(Instant.now());
        accountRepository.save(account);

        //Ledger entry
        LedgerEntry ledger = LedgerEntry.builder()
                .id(UUID.randomUUID())
                .txId(req.getTransactionId())
                .accountNumber(req.getAccountNumber())
                .amount(req.getAmount())
                .createdAt(Instant.now())
                .build();
        ledgerRepository.save(ledger);

        //update transaction
        tx.setStatus("DEBITED");
        transactionRepository.save(tx);

        //create idempotency record (Store minimal result)
        IdempotencyRecord idr = IdempotencyRecord.builder()
                .id(UUID.randomUUID())
                .txId(req.getTransactionId())
                .resultPayload("DEBITED")
                .createdAt(Instant.now())
                .build();
        idempotencyRepository.save(idr);

        return new DebitResponse(req.getTransactionId(), "SUCCESS", null);
    }
}
