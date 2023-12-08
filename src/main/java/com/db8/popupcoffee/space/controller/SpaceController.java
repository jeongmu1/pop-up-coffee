package com.db8.popupcoffee.space.controller;

import com.db8.popupcoffee.reservation.service.ReservationService;
import com.db8.popupcoffee.space.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/spaces")
@RequiredArgsConstructor
public class SpaceController {

    private final ReservationService reservationService;
    private final SpaceService spaceService;

    @GetMapping("/assignment")
    public String getAssignmentForm(Model model) {
        model.addAttribute("nonFixedFlexibles",
            reservationService.findNonFixedFlexibleRepositories());
        model.addAttribute("spaceInfos", spaceService.getReservationInfosOfSpaces());
        return "spaces/assignment";
    }
}
