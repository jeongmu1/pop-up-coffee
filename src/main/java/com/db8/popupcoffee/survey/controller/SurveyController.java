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
import com.db8.popupcoffee.survey.dto.response.SurveyInfo;
import com.db8.popupcoffee.survey.dto.response.SurveyItemInfo;
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

    private static final String SETTING_VIEW_REDIRECT = "redirect:/surveys/setting";
    private static final String LOGIN_REDIRECT = "redirect:/merchants/login";

    @ModelAttribute("surveys")
    public List<SurveyInfo> getSurveyInfos() {
        return surveyService.findAllSurveyInfos();
    }

    @GetMapping("/setting")
    public String surveySetting(Model model) {
        Survey survey = surveyService.createSurvey();
        List<SurveyItemInfo> previousItems = surveyService.getPreviousSurveyItems();
        List<SurveyItemSelected> additionalComments = surveyService.getAdditionalComments();
        List<SurveyItem> nextMonthItems = surveyService.getNextMonthSurveyItems();

        SurveySettingResponse response = new SurveySettingResponse(nextMonthItems, previousItems,
            additionalComments, survey);

        model.addAttribute("response", response);

        return "surveys/setting";
    }

    @PostMapping("/setting")
    public String surveySetting(SurveySettingRequest surveySettingRequest, List<Long> selectedItems,
        List<String> selectedaAdditionalComment) {
        surveyService.surveySetting(surveySettingRequest, selectedItems,
            selectedaAdditionalComment);

        return "home";
    }


    @PostMapping("/deleteSurveyItem")
    public String deleteSurveyItem(Long surveyItemId) {
        surveyService.deleteSurveyItem(surveyItemId);

        return SETTING_VIEW_REDIRECT;
    }

    @PostMapping("/deleteAdditionalComment")
    public String deleteAdditionalComment(Long additionalCommentId) {
        surveyService.deleteAdditionalComment(additionalCommentId);
        return SETTING_VIEW_REDIRECT;
    }

    @PostMapping("/addSurveyItem")
    public String addSurveyItem(SurveyItemRequest surveyItemRequest) {
        surveyService.addItemToSurvey(surveyItemRequest);

        return SETTING_VIEW_REDIRECT;
    }


    @GetMapping("/{surveyId}")
    public String showSurvey(@PathVariable Long surveyId, Model model, HttpSession session) {
        MemberSessionInfo sessionInfo = SessionUtil.getMemberSessionInfo(session);
        if (sessionInfo == null) {
            return LOGIN_REDIRECT;
        }

        boolean hasResponded = surveyService.hasRespondedSurvey(surveyId, sessionInfo.id());

        if (hasResponded) {
            throw new IllegalArgumentException("이미 이번달 설문을 참여하셨습니다.");
        }

        Survey survey = surveyService.getSurvey(surveyId);
        List<SurveyItemInfo> selectedItemCounts = surveyService.countSelectedItemsForThisMonth();

        model.addAttribute("survey", survey);
        model.addAttribute("items", survey.getItems());
        model.addAttribute("selectedItemCounts", selectedItemCounts);

        return "surveys/form";
    }

    @GetMapping()
    public String showCurrentSurvey(HttpSession session) {
        MemberSessionInfo sessionInfo = SessionUtil.getMemberSessionInfo(session);
        if (sessionInfo == null) {
            return LOGIN_REDIRECT;
        }
        long id = surveyService.findSurveyIdOfCurrentDate();
        return "redirect:/surveys/" + id;
    }

    @PostMapping("/{surveyId}/responses")
    public String submitResponse(@PathVariable Long surveyId,
        @ModelAttribute SurveyResponseRequest form, HttpSession session) {
        MemberSessionInfo sessionInfo = SessionUtil.getMemberSessionInfo(session);
        if (sessionInfo == null) {
            return LOGIN_REDIRECT;
        }
        surveyService.submitResponse(surveyId, sessionInfo.id(), form);
        memberService.increasePointAndSetLastSurveyed(sessionInfo.id());

        return "redirect:/";
    }

    @GetMapping("/{surveyId}/pie-chart")
    public String showPieChart(@PathVariable Long surveyId, Model model) {
        model.addAttribute("surveyItems", surveyService.findSurveyItems(surveyId));
        model.addAttribute("currentSurveyId", surveyId);
        return "admins/surveyStats";
    }

    @GetMapping("/result/pie-charts")
    public String showCurrentPieChart() {
        long id = surveyService.findSurveyIdOfCurrentDate();
        return "redirect:/surveys/" + id + "/pie-chart";
    }
}
