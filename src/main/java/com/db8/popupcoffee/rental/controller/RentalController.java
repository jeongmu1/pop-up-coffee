package com.db8.popupcoffee.rental.controller;

import com.db8.popupcoffee.rental.controller.dto.request.ChangeStatusRequest;
import com.db8.popupcoffee.rental.domain.SpaceRentalStatus;
import com.db8.popupcoffee.rental.service.RentalService;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @ModelAttribute("statuses")
    public List<SpaceRentalStatus> getStatuses() {
        return Arrays.stream(SpaceRentalStatus.values()).toList();
    }

    @GetMapping
    public String getRentalInfos(Model model) {
        model.addAttribute("rentals", rentalService.findRentalInfos());
        return "rentals/info";
    }

    @PatchMapping("/{rentalId}/status")
    public String updateRentalStatus(
        @PathVariable("rentalId") Long rentalId,
        ChangeStatusRequest request) {
        if (request == null || request.status() == null) {
            rentalService.updateToNextStatus(rentalId);
        } else {
            rentalService.updateRentalStatus(rentalId, request);
        }
        return "redirect:/rentals";
    }
}
