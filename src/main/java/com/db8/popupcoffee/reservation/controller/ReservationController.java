package com.db8.popupcoffee.reservation.controller;

import com.db8.popupcoffee.global.util.SessionKeys;
import com.db8.popupcoffee.merchant.controller.dto.MerchantSessionInfo;
import com.db8.popupcoffee.reservation.controller.dto.request.CreateFixedReservationRequest;
import com.db8.popupcoffee.reservation.controller.dto.request.CreateFlexibleReservationRequest;
import com.db8.popupcoffee.reservation.service.ReservationService;
import com.db8.popupcoffee.reservation.service.dto.CreateFixedReservationDto;
import com.db8.popupcoffee.reservation.service.dto.CreateFlexibleReservationDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/fixed")
    public String getFixedReservationForm() {
        return "reservations/fixed/form";
    }

    @PostMapping("/fixed")
    public String createFixedReservation(CreateFixedReservationRequest form, HttpSession session) {
        MerchantSessionInfo sessionInfo = (MerchantSessionInfo) session.getAttribute(
            SessionKeys.MERCHANT_SESSION_KEY);
        reservationService.progressFixedReservation(
            CreateFixedReservationDto.of(sessionInfo.id(), form));
        return "redirect:/reservations";
    }

    @PostMapping("/flexible")
    public String createFlexibleReservation(CreateFlexibleReservationRequest form,
        HttpSession session) {
        MerchantSessionInfo sessionInfo = (MerchantSessionInfo) session.getAttribute(
            SessionKeys.MERCHANT_SESSION_KEY);
        reservationService.progressFlexibleReservation(
            CreateFlexibleReservationDto.of(sessionInfo.id(), form));
        return "redirect:/reservations";
    }
}
