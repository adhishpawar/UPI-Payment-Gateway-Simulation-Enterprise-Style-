package com.upi.bankservice.Repositories;


import com.upi.bankservice.models.BankAccount;
import com.upi.bankservice.models.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

    Optional<BankAccount> findByAccountNumber(String accountNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from BankAccount a where a.accountNumber = :acc")
    Optional<BankAccount> findByAccountNumberForUpdate(@Param("acc") String accountNumber);

    List<BankAccount> findByUser(User user);
}