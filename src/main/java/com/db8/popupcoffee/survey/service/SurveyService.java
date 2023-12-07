package com.db8.popupcoffee.survey.service;

import com.db8.popupcoffee.global.domain.EmbeddableYearMonth;
import com.db8.popupcoffee.member.domain.Member;
import com.db8.popupcoffee.survey.domain.Survey;
import com.db8.popupcoffee.survey.domain.SurveyItem;
import com.db8.popupcoffee.survey.domain.SurveyItemSelected;
import com.db8.popupcoffee.survey.domain.SurveyResponse;
import com.db8.popupcoffee.survey.dto.request.SurveyItemRequest;
import com.db8.popupcoffee.survey.dto.request.SurveyItemSelectedRequest;
import com.db8.popupcoffee.survey.dto.request.SurveySettingRequest;
import com.db8.popupcoffee.survey.repository.SurveyItemRepository;
import com.db8.popupcoffee.survey.repository.SurveyItemSelectedRepository;
import com.db8.popupcoffee.survey.repository.SurveyRepository;
import com.db8.popupcoffee.survey.repository.SurveyResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final SurveyItemSelectedRepository surveyItemSelectedRepository;

    public List<SurveyItem> findAll() {
        return surveyItemRepository.findAll();
    }

    @Transactional
    public void surveySetting(SurveySettingRequest surveySettingRequest) {
        // 설문지 생성
        Survey survey = surveySettingRequest.toEntity();
        surveyRepository.save(survey);

        // 선택된 항목 설정
        List<SurveyItem> selectedItems = surveyItemRepository.findAllById(surveySettingRequest.selectedItems());
        selectedItems.forEach(item -> item.setSurvey(survey));
        surveyItemRepository.saveAll(selectedItems);
    }

    public List<SurveyItem> getPreviousSurveyItems() {
        YearMonth thisMonth = YearMonth.now();
        YearMonth lastMonth = thisMonth.minusMonths(1);

        EmbeddableYearMonth lastMonthYearMonth = new EmbeddableYearMonth(lastMonth.getYear(), lastMonth.getMonthValue());
        Survey lastMonthSurvey = surveyRepository.findByYearMonthOf(lastMonthYearMonth);

        List<SurveyItem> previousSurveyItems = new ArrayList<>();
        if (lastMonthSurvey != null) {
            previousSurveyItems.addAll(lastMonthSurvey.getItems());
        }

        return previousSurveyItems;
    }

    @Transactional
    public void addSurveyItem(SurveyItemRequest form) {
        SurveyItem surveyItem = form.toEntity();
        surveyItemRepository.save(surveyItem);
    }

    public Survey getSurvey(Long surveyId) {
        return surveyRepository.findById(surveyId).orElseThrow(null);
    }


    @Transactional
    public void saveResponse(Long surveyId, Member member, SurveyItemSelectedRequest request) {
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(null);
        SurveyResponse surveyResponse = SurveyResponse.createSurveyResponse(member, survey);
        surveyResponseRepository.save(surveyResponse);

        SurveyItemSelected surveyItemSelected = request.toEntity();
        surveyItemSelected.setSurveyResponse(surveyResponse);
        surveyItemSelectedRepository.save(surveyItemSelected);
    }

}
