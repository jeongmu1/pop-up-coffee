package com.db8.popupcoffee.home.controller;

import com.db8.popupcoffee.merchant.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final MerchantService merchantService;

    @GetMapping
    public String getMainView(Model model) {
        model.addAttribute("merchants", merchantService.getRankedMerchantRevenues());
        return "home";
    }
}
