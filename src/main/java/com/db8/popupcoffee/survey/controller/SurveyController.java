package com.db8.popupcoffee.survey.controller;

import com.db8.popupcoffee.global.util.SessionUtil;
import com.db8.popupcoffee.member.controller.dto.MemberSessionInfo;
import com.db8.popupcoffee.member.service.MemberService;
import com.db8.popupcoffee.survey.domain.Survey;
import com.db8.popupcoffee.survey.domain.SurveyItem;
import com.db8.popupcoffee.survey.dto.request.SurveyItemRequest;
import com.db8.popupcoffee.survey.dto.request.SurveyResponseRequest;
import com.db8.popupcoffee.survey.dto.request.SurveySettingRequest;
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
    public String surveySetting(Model model, HttpSession session) {
        MemberSessionInfo sessionInfo = SessionUtil.getMemberSessionInfo(session);
        if(sessionInfo == null) {
            return "redirect:/login";
        }

        List<SurveyItem> surveyItemList = surveyService.findAll();
        model.addAttribute("surveyItemList", surveyItemList);

        // 전 달 설문지의 아이템들 가져오기
        List<SurveyItem> previousItems = surveyService.getPreviousSurveyItems();
        model.addAttribute("previousItems", previousItems);

        List<String> additionalComments = surveyService.getAdditionalComments();
        model.addAttribute("additionalComments", additionalComments);

        return "surveys/setting";
    }

    @PostMapping("/setting")
    public String surveySetting(SurveySettingRequest surveySettingRequest, List<Long> selectedItems, List<String> selectedaAdditionalComment, HttpSession session) {
        MemberSessionInfo sessionInfo = SessionUtil.getMemberSessionInfo(session);
        if(sessionInfo == null) {
            return "redirect:/login";
        }

        surveyService.surveySetting(surveySettingRequest, selectedItems, selectedaAdditionalComment);

        return "";
    }

    @PostMapping("/addSurveyItem")
    public String addSurveyItem(SurveyItemRequest form, HttpSession session) {
        MemberSessionInfo sessionInfo = SessionUtil.getMemberSessionInfo(session);
        if(sessionInfo == null) {
            return "redirect:/login";
        }

        surveyService.addSurveyItem(form);

        return "";
    }

    @GetMapping("/{surveyId}")
    public String showSurvey(@PathVariable Long surveyId, Model model, HttpSession session) {
        MemberSessionInfo sessionInfo = SessionUtil.getMemberSessionInfo(session);
        if(sessionInfo == null) {
            return "redirect:/login";
        }

        Survey survey = surveyService.getSurvey(surveyId);
        List<Integer> selectedItemCounts = surveyService.countSelectedItems(survey);

        model.addAttribute("survey", survey);
        model.addAttribute("items", survey.getItems());
        model.addAttribute("selectedItemCounts", selectedItemCounts);

        return "surveys/show";
    }


    @PostMapping("/{surveyId}/responses")
    public String submitResponse(@PathVariable Long surveyId, Long memberId, SurveyResponseRequest surveyResponseRequest, @RequestParam List<Long> selectedItems, HttpSession session) {
        MemberSessionInfo sessionInfo = SessionUtil.getMemberSessionInfo(session);
        if(sessionInfo == null) {
            return "redirect:/login";
        }
        surveyService.submitResponse(surveyId, memberId, surveyResponseRequest, selectedItems);
        memberService.increasePointAndSetLastSurveyed(memberId);

        return "redirect:/surveys/";
    }

}
