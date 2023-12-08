package com.db8.popupcoffee.seasonality.controller;

import com.db8.popupcoffee.seasonality.controller.dto.request.CreateNormalDatesRequest;
import com.db8.popupcoffee.seasonality.controller.dto.response.DateInfoResponse;
import com.db8.popupcoffee.seasonality.controller.dto.response.SimpleSeasonalityLevel;
import com.db8.popupcoffee.seasonality.service.DateInfoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seasons")
@RequiredArgsConstructor
public class SeasonalityController {

    private final DateInfoService dateInfoService;

    @ModelAttribute("seasonalityLevels")
    public List<SimpleSeasonalityLevel> getSimpleSeasonalityLevels() {
        return SimpleSeasonalityLevel.simpleSeasonalityLevels();
    }

    @ModelAttribute("dateInfos")
    public List<DateInfoResponse> getDateInfos() {
        return dateInfoService.findDateInfos();
    }

    @PostMapping("/year")
    public String createNormalDateInfos(CreateNormalDatesRequest request) {
        dateInfoService.createDatesOfYear(request);
        return "seasonality/form";
    }

    @GetMapping("/form")
    public String getDateInfosForm() {
        return "seasonality/form";
    }
}
