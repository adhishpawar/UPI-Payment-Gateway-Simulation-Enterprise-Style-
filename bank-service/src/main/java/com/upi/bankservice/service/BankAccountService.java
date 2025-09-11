package com.upi.bankservice.service;

import com.upi.bankservice.DTOs.*;
import com.upi.bankservice.Repositories.*;
import com.upi.bankservice.exception.InsufficientFundsException;
import com.upi.bankservice.models.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final IdempotencyRepository idempotencyRepository;
    private final LedgerRepository ledgerRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository,
                              TransactionRepository transactionRepository,
                              IdempotencyRepository idempotencyRepository,
                              LedgerRepository ledgerRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.idempotencyRepository = idempotencyRepository;
        this.ledgerRepository = ledgerRepository;
    }

    public BankAccount linkBankAccount(BankAccountRequest request, User user) {
        BankAccount account = BankAccount.builder()
                .id(UUID.randomUUID())
                .accountNumber(request.getAccountNumber())
                .bankId(request.getBankId())
                .balance(BigDecimal.valueOf(1000.00)) // mock initial balance
                .user(user)
                .updatedAt(Instant.now())
                .build();
        return bankAccountRepository.save(account);
    }

    /** List user’s linked accounts */
    public List<BankAccount> listAccounts(User user) {
        return bankAccountRepository.findByUser(user);
    }

    /** Debit operation with idempotency, ledger, transaction log */
    @Transactional
    public DebitResponse debit(DebitRequest req) {
        // 1. Idempotency check
        Optional<IdempotencyRecord> existing = idempotencyRepository.findByTxId(req.getTransactionId());
        if (existing.isPresent()) {
            return new DebitResponse(req.getTransactionId(), "SUCCESS", "Already processed");
        }

        // 2. Create transaction record
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

        // 3. Lock account row
        BankAccount account = bankAccountRepository.findByAccountNumberForUpdate(req.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found: " + req.getAccountNumber()));

        // 4. Check funds
        if (account.getBalance().compareTo(req.getAmount()) < 0) {
            tx.setStatus("FAILED");
            transactionRepository.save(tx);
            throw new InsufficientFundsException("Insufficient Funds");
        }

        // 5. Debit account
        account.setBalance(account.getBalance().subtract(req.getAmount()));
        account.setUpdatedAt(Instant.now());
        bankAccountRepository.save(account);

        // 6. Ledger entry
        LedgerEntry ledger = LedgerEntry.builder()
                .id(UUID.randomUUID())
                .txId(req.getTransactionId())
                .accountNumber(req.getAccountNumber())
                .amount(req.getAmount())
                .createdAt(Instant.now())
                .build();
        ledgerRepository.save(ledger);

        // 7. Update transaction
        tx.setStatus("DEBITED");
        transactionRepository.save(tx);

        // 8. Save idempotency record
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