package com.db8.popupcoffee.merchant.controller;

import com.db8.popupcoffee.global.util.SessionUtil;
import com.db8.popupcoffee.merchant.controller.dto.MerchantSessionInfo;
import com.db8.popupcoffee.merchant.controller.dto.request.CreateMerchantRequest;
import com.db8.popupcoffee.merchant.controller.dto.request.MerchantLoginForm;
import com.db8.popupcoffee.merchant.controller.dto.response.MyPageResponse;
import com.db8.popupcoffee.merchant.domain.BusinessType;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.service.MerchantService;
import com.db8.popupcoffee.reservation.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;
    private final ReservationService reservationService;

    @ModelAttribute("businessTypes")
    public List<BusinessType> getBusinessTypes() {
        return merchantService.findBusinessTypes();
    }

    @GetMapping("/form")
    public String getMerchantCreatingForm() {
        return "admins/merchantsRegistration";
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
        session.setAttribute(SessionUtil.MERCHANT_SESSION_KEY, MerchantSessionInfo.from(merchant));
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute(SessionUtil.MERCHANT_SESSION_KEY, null);
        return "redirect:/";
    }

    @GetMapping("/myPage")
    public String getMypage(HttpSession session, Model model) {
        MerchantSessionInfo merchantInfo = SessionUtil.getMerchantSessionInfo(session);
        if (merchantInfo == null) {
            return "redirect:/merchants/login";
        }
        Merchant merchant = merchantService.getMerchantInfo(merchantInfo.id());
        int scoreForNextGrade = merchantService.getScoreForNextGrade(merchantInfo.id());
        int currentGradeMinScore = merchantService.getCurrentGradeMinScore(merchantInfo.id());
        int nextGradeMinScore = merchantService.getNextGradeMinScore(merchantInfo.id());
        MyPageResponse myPageResponse = new MyPageResponse(merchant, scoreForNextGrade, currentGradeMinScore, nextGradeMinScore);

        model.addAttribute("myPageResponse", myPageResponse);
        model.addAttribute("fixed", reservationService.getFixedHistories(
                SessionUtil.getMerchantSessionInfo(session).id()));
        model.addAttribute("nonFixedFlexibles",
                reservationService.findAllFlexibleReservations());

        return "merchants/myPage";
    }
}
