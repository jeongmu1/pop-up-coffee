package com.db8.popupcoffee.survey.service;

import com.db8.popupcoffee.global.domain.EmbeddableYearMonth;
import com.db8.popupcoffee.member.domain.Member;
import com.db8.popupcoffee.member.repository.MemberRepository;
import com.db8.popupcoffee.survey.domain.Survey;
import com.db8.popupcoffee.survey.domain.SurveyItem;
import com.db8.popupcoffee.survey.domain.SurveyItemSelected;
import com.db8.popupcoffee.survey.domain.SurveyResponse;
import com.db8.popupcoffee.survey.dto.request.SurveyItemRequest;
import com.db8.popupcoffee.survey.dto.request.SurveyResponseRequest;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final SurveyItemSelectedRepository surveyItemSelectedRepository;
    private final MemberRepository memberRepository;

    public List<SurveyItem> findAll() {
        return surveyItemRepository.findAll();
    }

    @Transactional
    public void surveySetting(SurveySettingRequest surveySettingRequest, List<Long> selectedItemsId, List<String> selectedaAdditionalComment) {
        // 설문지 생성
        Survey survey = surveySettingRequest.toEntity();
        surveyRepository.save(survey);

        selectedaAdditionalComment.stream().map(comment -> SurveyItem.createItem(comment, survey)).forEach(surveyItemRepository::save);

        // 선택된 항목 설정
        List<SurveyItem> selectedItems = surveyItemRepository.findAllById(selectedItemsId);
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

    public List<String> getAdditionalComments() {
        return surveyItemSelectedRepository.findAll().stream()
                .map(SurveyItemSelected::getAdditionalComment)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addSurveyItem(SurveyItemRequest form) {
        SurveyItem surveyItem = form.toEntity();
        surveyItemRepository.save(surveyItem);
    }

    public Survey getSurvey(Long surveyId) {
        return surveyRepository.findById(surveyId).orElseThrow(null);
    }

    public List<Integer> countSelectedItems(Survey survey) {
        List<Integer> counts = survey.getItems().stream().mapToInt(surveyItemSelectedRepository::countByItem).boxed().collect(Collectors.toList());
        return counts;
    }

    @Transactional
    public void submitResponse(Long surveyId, Long memberId, SurveyResponseRequest surveyResponseRequest, List<Long> selectedItems) {
        Member member = memberRepository.findById(memberId).orElseThrow(null);
        Survey survey = getSurvey(surveyId);

        // 설문응답 생성
        SurveyResponse surveyResponse = surveyResponseRequest.toSurveyResponse(member, survey);
        surveyResponseRepository.save(surveyResponse);

        // 선택된 항목 저장
        List<SurveyItemSelected> surveyItemSelecteds = surveyResponseRequest.toSurveyItemSelecteds(selectedItems, surveyResponse, surveyItemRepository);
        surveyItemSelectedRepository.saveAll(surveyItemSelecteds);
    }


}
