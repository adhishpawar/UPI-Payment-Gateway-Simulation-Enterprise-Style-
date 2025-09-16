package com.upi.psp.service;

import com.upi.psp.dto.VPARequest;
import com.upi.psp.dto.VPAResponse;
import com.upi.psp.model.VPA;
import com.upi.psp.repo.VpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VpaService {
    private final VpaRepository vpaRepository;

    public VpaService(VpaRepository vpaRepository) {
        this.vpaRepository = vpaRepository;
    }

    public VPA createVPA(VPARequest req) {
        String vpaAddress = req.getUserId().toString() + "@psp";
        if (vpaRepository.existsByVpaAddress(vpaAddress)) {
            throw new RuntimeException("VPA already exists");
        }

        VPA vpa = new VPA();
        vpa.setVpaId(UUID.randomUUID().toString());
        vpa.setUserId(req.getUserId());
        vpa.setBankAccountId(req.getBankAccountId());
        vpa.setPspId(req.getPspId());
        vpa.setVpaAddress(vpaAddress);
        vpa.setCreatedAt(Instant.now());

        return vpaRepository.save(vpa);
    }

    public List<VPAResponse> getVPAsByUser(UUID userId) {
        return vpaRepository.findByUserId(userId)
                .stream()
                .map(VPAResponse::from)
                .toList();
    }
}
