package com.db8.popupcoffee.merchant.controller;

import com.db8.popupcoffee.merchant.controller.dto.request.CreateMerchantRequest;
import com.db8.popupcoffee.merchant.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private MerchantService merchantService;

    @GetMapping("/form")
    public String getMerchantCreatingForm() {
        return "merchants/form";
    }

    @PostMapping
    public String createMerchant(CreateMerchantRequest form) {
        merchantService.createMerchant(form);
        return "redirect:/merchants";
    }
}
