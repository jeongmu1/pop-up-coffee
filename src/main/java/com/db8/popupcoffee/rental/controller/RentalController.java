package com.db8.popupcoffee.rental.controller;

import com.db8.popupcoffee.rental.controller.dto.request.ChangeStatusRequest;
import com.db8.popupcoffee.rental.controller.dto.request.SpaceRentalRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public String createSpaceRental(SpaceRentalRequest spaceRentalRequest) {
        rentalService.createSpaceRental(spaceRentalRequest);
        return "redirect:/rentals";
    }

    @PatchMapping("/status")
    public String updateRentalStatus(ChangeStatusRequest request) {
        rentalService.updateRentalStatus(request);
        return "redirect:/rentals";
    }
}
