package com.upi.bankservice.controller;


import com.upi.bankservice.DTOs.*;
import com.upi.bankservice.models.*;
import com.upi.bankservice.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vpa")
@RequiredArgsConstructor
public class VPAController {
    private final VPAService vpaService;
    private final UserService userService;

    @PostMapping("/create")
    public VPA create(@RequestBody VPARequest request, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return vpaService.createVPA(request, user);
    }

    @GetMapping("/list")
    public List<VPA> list(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return vpaService.listVPAs(user);
    }
}
