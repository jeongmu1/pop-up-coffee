package com.db8.popupcoffee.rental.controller;

import com.db8.popupcoffee.rental.controller.dto.request.SpaceRentalRequest;
import com.db8.popupcoffee.rental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

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
}
