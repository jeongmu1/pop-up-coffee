package com.db8.popupcoffee.merchant.controller;

import com.db8.popupcoffee.global.util.SessionKeys;
import com.db8.popupcoffee.merchant.controller.dto.MerchantSessionInfo;
import com.db8.popupcoffee.merchant.controller.dto.request.CreateMerchantRequest;
import com.db8.popupcoffee.merchant.controller.dto.request.MerchantLoginForm;
import com.db8.popupcoffee.merchant.domain.BusinessType;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.service.MerchantService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @ModelAttribute("businessTypes")
    public List<BusinessType> getBusinessTypes() {
        return merchantService.findBusinessTypes();
    }

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
        session.setAttribute(SessionKeys.MERCHANT_SESSION_KEY, MerchantSessionInfo.from(merchant));
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute(SessionKeys.MERCHANT_SESSION_KEY, null);
        return "redirect:/";
    }
}
