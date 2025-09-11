package com.upi.bankservice.service;

import com.upi.bankservice.DTOs.VPARequest;
import com.upi.bankservice.Repositories.VPARepository;
import com.upi.bankservice.models.User;
import com.upi.bankservice.models.VPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VPAService {
    private final VPARepository vpaRepository;

    public VPA createVPA(VPARequest request, User user) {
        VPA vpa = VPA.builder()
                .vpa(request.getVpa())
                .user(user)
                .build();
        return vpaRepository.save(vpa);
    }

    public List<VPA> listVPAs(User user) {
        return vpaRepository.findByUser(user);
    }
}