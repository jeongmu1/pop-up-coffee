package com.db8.popupcoffee.reservation.controller;

import com.db8.popupcoffee.global.util.SessionUtil;
import com.db8.popupcoffee.merchant.controller.dto.MerchantSessionInfo;
import com.db8.popupcoffee.merchant.domain.BusinessType;
import com.db8.popupcoffee.merchant.service.MerchantService;
import com.db8.popupcoffee.reservation.controller.dto.request.CreateFixedReservationRequest;
import com.db8.popupcoffee.reservation.controller.dto.request.CreateFlexibleReservationRequest;
import com.db8.popupcoffee.reservation.controller.dto.response.FlexibleReservationInfo;
import com.db8.popupcoffee.seasonality.controller.dto.response.DateInfoResponse;
import com.db8.popupcoffee.reservation.controller.dto.response.FeeInfo;
import com.db8.popupcoffee.reservation.service.ReservationService;
import com.db8.popupcoffee.reservation.service.dto.CreateFixedReservationDto;
import com.db8.popupcoffee.reservation.service.dto.CreateFlexibleReservationDto;
import com.db8.popupcoffee.reservation.service.dto.FixedDatesInfoDto;
import com.db8.popupcoffee.seasonality.service.DateInfoService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final DateInfoService dateInfoService;
    private final MerchantService merchantService;

    @ModelAttribute("dateInfos")
    public List<DateInfoResponse> getDateInfos() {
        return dateInfoService.findDateInfos();
    }

    @ModelAttribute("businessTypes")
    public List<BusinessType> getBusinessTypes() {
        return merchantService.findBusinessTypes();
    }

    @ModelAttribute("nonFixedFlexibles")
    public List<FlexibleReservationInfo> getNonFixedFlexibleReservations() {
        return reservationService.findNonFixedFlexibleRepositories();
    }

    @GetMapping("/histories")
    public String getReservationHistories(Model model, HttpSession session) {
        model.addAttribute("histories", reservationService.getReservationHistories(
            SessionUtil.getMerchantSessionInfo(session).id()));
        return null;
    }

    @GetMapping("/fixed/form")
    public String getFixedReservationForm() {
        return "reservations/fixed/form";
    }

    @GetMapping("/fixed/fee")
    public String getFeeOfDates(@RequestParam LocalDate start, @RequestParam LocalDate end,
        HttpSession session, Model model) {
        MerchantSessionInfo sessionInfo = SessionUtil.getMerchantSessionInfo(session);
        FeeInfo feeInfo = reservationService.getFeeInfo(
            new FixedDatesInfoDto(sessionInfo.id(), start, end));
        model.addAttribute("feeInfo", feeInfo);
        return "reservations/fixed/form";
    }

    @PostMapping("/fixed")
    public String createFixedReservation(CreateFixedReservationRequest form, HttpSession session) {
        MerchantSessionInfo sessionInfo = SessionUtil.getMerchantSessionInfo(session);
        reservationService.progressFixedReservation(
            CreateFixedReservationDto.of(sessionInfo.id(), form));
        return "redirect:/reservations";
    }

    @GetMapping("/flexible/form")
    public String getFlexibleReservationForm() {
        return "reservations/flexible/form";
    }

    @PostMapping("/flexible")
    public String createFlexibleReservation(CreateFlexibleReservationRequest form,
        HttpSession session) {
        MerchantSessionInfo sessionInfo = SessionUtil.getMerchantSessionInfo(session);
        reservationService.progressFlexibleReservation(
            CreateFlexibleReservationDto.of(sessionInfo.id(), form));
        return "redirect:/reservations";
    }

    @GetMapping("/space")
    public String getSpaceAssignmentForm() {
        return "admins/spaceManagement";
    }
}
