package com.upi.psp.service;

import com.upi.psp.dto.*;
import com.upi.psp.model.VPA;
import com.upi.psp.dto.PaymentInitResponse;
import com.upi.psp.repo.VpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final VpaRepository vpaRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String BANK_SERVICE_URL = "http://localhost:8081/accounts"; // ✅ BankService base URL

    // ---------------- Step 1: Initiate Payment ----------------
    public PaymentInitResponse initiatePayment(PaymentInitRequest request) {
        // Validate payer VPA exists
        Optional<VPA> payerVpa = vpaRepository.findByVpaAddress(request.getPayerVpa());
        if (payerVpa.isEmpty()) {
            return new PaymentInitResponse(request.getTxId(), "FAILED", "Payer VPA not found");
        }

        // Validate payee VPA exists
        Optional<VPA> payeeVpa = vpaRepository.findByVpaAddress(request.getPayeeVpa());
        if (payeeVpa.isEmpty()) {
            return new PaymentInitResponse(request.getTxId(), "FAILED", "Payee VPA not found");
        }

        // For now we simulate NPCI routing
        return new PaymentInitResponse(request.getTxId(), "INITIATED", "Sent to NPCI for routing");
    }

    // ---------------- Step 2: Validate Payee ----------------
    public PayeeValidationResponse validatePayee(PayeeValidationRequest request) {
        Optional<VPA> vpa = vpaRepository.findByVpaAddress(request.getPayeeVpa());
        if (vpa.isPresent()) {
            return new PayeeValidationResponse(request.getPayeeVpa(), true, "Payee exists");
        }
        return new PayeeValidationResponse(request.getPayeeVpa(), false, "Payee not found");
    }

    // ---------------- Step 3: Debit payer account ----------------
    public TransactionResponse debit(String txId, String accountNumber, BigDecimal amount) {
        String url = BANK_SERVICE_URL + "/" + accountNumber + "/debit";
        TransactionRequest req = new TransactionRequest(txId, amount);
        return restTemplate.patchForObject(url, req, TransactionResponse.class);
    }

    // ---------------- Step 4: Credit payee account ----------------
    public TransactionResponse credit(String txId, String accountNumber, BigDecimal amount) {
        String url = BANK_SERVICE_URL + "/" + accountNumber + "/credit";
        TransactionRequest req = new TransactionRequest(txId, amount);
        return restTemplate.patchForObject(url, req, TransactionResponse.class);
    }
}
