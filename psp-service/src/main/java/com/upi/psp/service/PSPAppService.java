package com.upi.psp.service;

import com.upi.psp.model.PSPApp;
import com.upi.psp.repo.PSPAppRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PSPAppService {

    private final PSPAppRepository appRepository;

    public PSPAppService(PSPAppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public Optional<PSPApp> getByHandle(String handle) {
        return appRepository.findByUpiHandle(handle);
    }

    public PSPApp save(PSPApp app) {
        return appRepository.save(app);
    }
}
