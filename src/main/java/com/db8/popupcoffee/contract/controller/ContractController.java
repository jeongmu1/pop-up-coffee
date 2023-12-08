package com.db8.popupcoffee.contract.controller;

import com.db8.popupcoffee.contract.controller.dto.request.CreateContractRequest;
import com.db8.popupcoffee.contract.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping("/form")
    public String getContractForm() {
        return "contracts/form";
    }

    @PostMapping
    public String createContract(CreateContractRequest createContractRequest) {
        contractService.processContract(createContractRequest);
        return "redirect:/contracts";
    }
}
