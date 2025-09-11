package com.upi.bankservice.Repositories;


import com.upi.bankservice.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface VPARepository extends JpaRepository<VPA, Long> {
    List<VPA> findByUser(User user);
    Optional<VPA> findByVpa(String vpa);
}
