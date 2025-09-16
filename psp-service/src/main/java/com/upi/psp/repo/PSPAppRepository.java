package com.upi.psp.repo;

import com.upi.psp.model.PSPApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PSPAppRepository extends JpaRepository<PSPApp, String> {
    Optional<PSPApp> findByUpiHandle(String upiHandle);
}
