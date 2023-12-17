package com.db8.popupcoffee.settlement.controller;

import com.db8.popupcoffee.member.service.MemberService;
import com.db8.popupcoffee.rental.service.RentalService;
import com.db8.popupcoffee.settlement.controller.dto.request.OrderForm;
import com.db8.popupcoffee.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;
    private final MemberService memberService;
    private final RentalService rentalService;

    @GetMapping("/orders/form")
    public String getProductOrderForm(Model model) {
        model.addAttribute("members", memberService.findAllMembers());
        model.addAttribute("rentals", rentalService.findRentalInfos());
        return "settlements/orders/form";
    }

    @PostMapping("/orders")
    public String createProductOrder(OrderForm form) {
        settlementService.createProductOrder(form);
        return "redirect:/settlements/orders/form";
    }
}
