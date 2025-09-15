package com.upi.bankservice.service;

import com.upi.bankservice.dto.TransactionResponse;
import com.upi.bankservice.models.BankAccount;
import com.upi.bankservice.models.Idempotency;
import com.upi.bankservice.models.Ledger;
import com.upi.bankservice.Repositories.BankAccountRepository;
import com.upi.bankservice.Repositories.IdempotencyRepository;
import com.upi.bankservice.Repositories.LedgerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    @Autowired
    private BankAccountRepository accountRepository;
    @Autowired
    private LedgerRepository ledgerRepository;
    @Autowired
    private IdempotencyRepository idempotencyRepository;

    // Create Bank Account
    public BankAccount createAccount(BankAccount account) {
        account.setBalance(BigDecimal.ZERO); // initialize balance
        return accountRepository.save(account);
    }

    // Get all accounts for a user
    public List<BankAccount> getAccountsByUser(UUID userId) {
        return accountRepository.findByUserId(userId);
    }

    // Get account by account number
    public Optional<BankAccount> getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    // ---------- Debit Operation ----------
    @Transactional
    public TransactionResponse debit(String txId, String accountNumber, BigDecimal amount) {
        // Idempotency check
        Optional<Idempotency> existing = idempotencyRepository.findByTxId(txId);
        if (existing.isPresent()) {
            return new TransactionResponse(
                    txId,
                    accountNumber,
                    amount,
                    "DEBIT",
                    "SUCCESS",
                    new BigDecimal(existing.get().getResult())
            );
        }

        // Lock row for update
        BankAccount account = accountRepository.findByAccountNumberForUpdate(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            saveLedger(txId, accountNumber, amount, Ledger.TransactionType.DEBIT, Ledger.TransactionStatus.FAILED, account.getBalance());
            throw new IllegalStateException("Insufficient balance");
        }

        // Subtract amount from balance
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        saveLedger(txId, accountNumber, amount, Ledger.TransactionType.DEBIT, Ledger.TransactionStatus.SUCCESS, account.getBalance());

        // Save idempotency with balance value
        saveIdempotency(txId, account.getBalance().toString());

        return new TransactionResponse(txId, accountNumber, amount, "DEBIT", "SUCCESS", account.getBalance());
    }

    // ---------- Credit Operation ----------
    @Transactional
    public TransactionResponse credit(String txId, String accountNumber, BigDecimal amount) {
        // Idempotency check
        Optional<Idempotency> existing = idempotencyRepository.findByTxId(txId);
        if (existing.isPresent()) {
            return new TransactionResponse(
                    txId,
                    accountNumber,
                    amount,
                    "CREDIT",
                    "SUCCESS",
                    new BigDecimal(existing.get().getResult())
            );
        }

        // Lock row for update
        BankAccount account = accountRepository.findByAccountNumberForUpdate(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Add amount to balance
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        saveLedger(txId, accountNumber, amount, Ledger.TransactionType.CREDIT, Ledger.TransactionStatus.SUCCESS, account.getBalance());

        saveIdempotency(txId, account.getBalance().toString());

        return new TransactionResponse(txId, accountNumber, amount, "CREDIT", "SUCCESS", account.getBalance());
    }

    // ---------- Helper methods ----------
    private void saveLedger(String txId, String accountNumber, BigDecimal amount,
                            Ledger.TransactionType type, Ledger.TransactionStatus status, BigDecimal balanceAfter) {

        Ledger ledger = Ledger.builder()
                .txId(txId)
                .accountNumber(accountNumber)
                .amount(amount)
                .type(type)
                .status(status)
                .balanceAfter(balanceAfter)
                .build();

        ledgerRepository.save(ledger);
    }

    private void saveIdempotency(String txId, String result) {
        Idempotency idempotency = Idempotency.builder()
                .txId(txId)
                .result(result)
                .build();
        idempotencyRepository.save(idempotency);
    }
}
