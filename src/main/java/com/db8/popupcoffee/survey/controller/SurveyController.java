package com.db8.popupcoffee.survey.controller;

import com.db8.popupcoffee.survey.domain.Survey;
import com.db8.popupcoffee.survey.domain.SurveyItem;
import com.db8.popupcoffee.survey.dto.request.SurveyItemRequest;
import com.db8.popupcoffee.survey.dto.request.SurveyResponseRequest;
import com.db8.popupcoffee.survey.dto.request.SurveySettingRequest;
import com.db8.popupcoffee.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping("/setting")
    public String surveySetting(Model model) {
        List<SurveyItem> surveyItemList = surveyService.findAll();
        model.addAttribute("surveyItemList", surveyItemList);

        // 전 달 설문지의 아이템들 가져오기
        List<SurveyItem> previousItems = surveyService.getPreviousSurveyItems();
        model.addAttribute("previousItems", previousItems);

        return "surveys/setting";
    }

    @PostMapping("/setting")
    public String surveySetting(SurveySettingRequest surveySettingRequest) {
        surveyService.surveySetting(surveySettingRequest);

        return "";
    }

    @PostMapping("/addSurveyItem")
    public String addSurveyItem(SurveyItemRequest form) {
        surveyService.addSurveyItem(form);

        return "";
    }

    @GetMapping("/{surveyId}")
    public String showSurvey(@PathVariable Long surveyId, Model model) {
        Survey survey = surveyService.getSurvey(surveyId);
        model.addAttribute("survey", survey);
        model.addAttribute("items", survey.getItems());

        return "surveys/show";
    }

    @PostMapping("/{surveyId}/responses")
    public String submitResponse(@PathVariable Long surveyId, @RequestParam Long memberId, @RequestBody SurveyResponseRequest surveyResponseRequest) {
        surveyService.submitResponse(surveyId, memberId, surveyResponseRequest);

        return "redirect:/surveys/";
    }


}
