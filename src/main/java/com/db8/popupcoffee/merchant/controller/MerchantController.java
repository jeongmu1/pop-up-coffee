package com.db8.popupcoffee.merchant.controller;

import com.db8.popupcoffee.merchant.controller.dto.MerchantSessionInfo;
import com.db8.popupcoffee.merchant.controller.dto.request.CreateMerchantRequest;
import com.db8.popupcoffee.merchant.controller.dto.request.MerchantLoginForm;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.service.MerchantService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/form")
    public String getMerchantCreatingForm() {
        return "merchants/form";
    }

    @PostMapping
    public String createMerchant(CreateMerchantRequest form) {
        merchantService.createMerchant(form);
        return "redirect:/merchants";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "merchants/login";
    }

    @PostMapping("/login")
    public String progressLogin(MerchantLoginForm form, HttpSession session) {
        Merchant merchant = merchantService.findMerchant(form);
        session.setAttribute("merchant", MerchantSessionInfo.from(merchant));
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("merchant", null);
        return "redirect:/";
    }
}
