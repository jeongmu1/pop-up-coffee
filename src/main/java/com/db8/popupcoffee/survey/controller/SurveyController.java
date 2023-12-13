package com.db8.popupcoffee.survey.controller;

import com.db8.popupcoffee.global.util.SessionUtil;
import com.db8.popupcoffee.member.controller.dto.MemberSessionInfo;
import com.db8.popupcoffee.member.service.MemberService;
import com.db8.popupcoffee.survey.domain.Survey;
import com.db8.popupcoffee.survey.domain.SurveyItem;
import com.db8.popupcoffee.survey.domain.SurveyItemSelected;
import com.db8.popupcoffee.survey.dto.request.SurveyItemRequest;
import com.db8.popupcoffee.survey.dto.request.SurveyResponseRequest;
import com.db8.popupcoffee.survey.dto.request.SurveySettingRequest;
import com.db8.popupcoffee.survey.dto.response.SurveySettingResponse;
import com.db8.popupcoffee.survey.service.SurveyService;
import jakarta.servlet.http.HttpSession;
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
    private final MemberService memberService;

    @GetMapping("/setting")
    public String surveySetting(Model model) {
        Survey survey = surveyService.createSurvey();
        List<SurveyItem> previousItems = surveyService.getPreviousSurveyItems();
        List<SurveyItemSelected> additionalComments = surveyService.getAdditionalComments();
        List<SurveyItem> nextMonthItems = surveyService.getNextMonthSurveyItems(); // 추가된 코드

        SurveySettingResponse response = new SurveySettingResponse(nextMonthItems, previousItems, additionalComments, survey);

        model.addAttribute("response", response);

        return "surveys/setting";
    }

    @PostMapping("/setting")
    public String surveySetting(SurveySettingRequest surveySettingRequest, List<Long> selectedItems, List<String> selectedaAdditionalComment) {
        surveyService.surveySetting(surveySettingRequest, selectedItems, selectedaAdditionalComment);

        return "home";
    }


    @PostMapping("/deleteSurveyItem")
    public String deleteSurveyItem(Long surveyItemId) {
        surveyService.deleteSurveyItem(surveyItemId);

        return "redirect:/surveys/setting";
    }

    @PostMapping("/deleteAdditionalComment")
    public String deleteAdditionalComment(Long additionalCommentId) {
        surveyService.deleteAdditionalComment(additionalCommentId);
        return "redirect:/surveys/setting";
    }

    @PostMapping("/addSurveyItem")
    public String addSurveyItem(SurveyItemRequest surveyItemRequest) {
        surveyService.addItemToSurvey(surveyItemRequest);

        return "redirect:/surveys/setting";
    }


    @GetMapping("/{surveyId}")
    public String showSurvey(@PathVariable Long surveyId, Model model, HttpSession session) {
        MemberSessionInfo sessionInfo = SessionUtil.getMemberSessionInfo(session);
        if(sessionInfo == null) {
            return "redirect:/merchants/login";
        }
        Survey survey = surveyService.getSurvey(surveyId);
        List<Integer> selectedItemCounts = surveyService.countSelectedItems(survey);

        model.addAttribute("survey", survey);
        model.addAttribute("items", survey.getItems());
        model.addAttribute("selectedItemCounts", selectedItemCounts);

        return "surveys/form";
    }


    @PostMapping("/{surveyId}/responses")
    public String submitResponse(@PathVariable Long surveyId, @ModelAttribute SurveyResponseRequest form, HttpSession session) {
        MemberSessionInfo sessionInfo = SessionUtil.getMemberSessionInfo(session);
        if(sessionInfo == null) {
            return "redirect:/merchants/login";
        }
        surveyService.submitResponse(surveyId, sessionInfo.id(), form);
        memberService.increasePointAndSetLastSurveyed(sessionInfo.id());

        return "home";
    }


}
