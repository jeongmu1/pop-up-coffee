package com.db8.popupcoffee.space.controller;

import com.db8.popupcoffee.reservation.service.ReservationService;
import com.db8.popupcoffee.space.controller.dto.request.UnAssignmentRequest;
import com.db8.popupcoffee.space.controller.dto.response.SpaceInfo;
import com.db8.popupcoffee.space.service.SpaceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/spaces")
@RequiredArgsConstructor
public class SpaceController {

    private final ReservationService reservationService;
    private final SpaceService spaceService;

    @ModelAttribute("spaces")
    public List<SpaceInfo> findAllSpaces() {
        return spaceService.findAllSpaces();
    }

    @GetMapping("/assignment")
    public String getAssignmentForm(Model model) {
        model.addAttribute("nonFixedFlexibles",
            reservationService.findNonFixedFlexibleRepositories());
        model.addAttribute("spaceInfos", spaceService.getReservationInfosOfSpaces());
        return "spaces/assignment";
    }

    @DeleteMapping("/assignment")
    public String unAssignReservation(UnAssignmentRequest request) {
        spaceService.unAssignSpace(request);
        return "redirect:/spaces/assignment";
    }
}
