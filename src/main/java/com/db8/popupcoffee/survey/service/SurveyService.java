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
import com.db8.popupcoffee.survey.dto.response.SurveyItemInfo;
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
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final SurveyItemSelectedRepository surveyItemSelectedRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void surveySetting(SurveySettingRequest surveySettingRequest, List<Long> selectedItemsId, List<String> selectedAdditionalComment) {
        // 설문지 생성
        Survey survey = surveySettingRequest.toEntity();
        surveyRepository.save(survey);

        // '기타' 항목을 생성하고 저장합니다.
        SurveyItem additionalItem = SurveyItem.createItem("기타", survey);
        surveyItemRepository.save(additionalItem);

        selectedAdditionalComment.stream().map(comment -> SurveyItem.createItem(comment, survey)).forEach(surveyItemRepository::save);

        // 선택된 항목 설정
        List<SurveyItem> selectedItems = surveyItemRepository.findAllById(selectedItemsId);
        selectedItems.forEach(item -> item.setSurvey(survey));
        surveyItemRepository.saveAll(selectedItems);
    }


    @Transactional(readOnly = true)
    public List<SurveyItemInfo> getPreviousSurveyItems() {
        YearMonth thisMonth = YearMonth.now();

        EmbeddableYearMonth lastMonthYearMonth = new EmbeddableYearMonth(thisMonth.getYear(), thisMonth.getMonthValue());
        Survey lastMonthSurvey = surveyRepository.findByYearMonthOf(lastMonthYearMonth);

        List<SurveyItem> previousSurveyItems = new ArrayList<>();
        if (lastMonthSurvey != null) {
            previousSurveyItems.addAll(lastMonthSurvey.getItems());
        }

        return previousSurveyItems.stream().map(SurveyItemInfo::from).toList();
    }

    public List<SurveyItemSelected> getAdditionalComments() {
        YearMonth thisMonth = YearMonth.now();
        EmbeddableYearMonth lastMonthYearMonth = new EmbeddableYearMonth(thisMonth.getYear(), thisMonth.getMonthValue());

        // 저번 달의 설문조사 정보를 가져옵니다.
        Survey lastMonthSurvey = surveyRepository.findByYearMonthOf(lastMonthYearMonth);

        // 설문조사 정보가 null이 아니면 해당 설문조사의 항목 중 '기타' 항목을 필터링합니다.
        if (lastMonthSurvey != null) {
            List<SurveyItem> lastMonthSurveyItems = lastMonthSurvey.getItems();
            return surveyItemSelectedRepository.findAll().stream()
                    .filter(item -> lastMonthSurveyItems.contains(item.getItem()) && item.getAdditionalComment() != null)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }


    public Survey getSurvey(Long surveyId) {
        return surveyRepository.findById(surveyId).orElseThrow(null);
    }

    @Transactional(readOnly = true)
    public List<SurveyItemInfo> countSelectedItemsForThisMonth() {
        YearMonth thisMonth = YearMonth.now();
        EmbeddableYearMonth thisMonthYearMonth = new EmbeddableYearMonth(thisMonth.getYear(), thisMonth.getMonthValue());

        Survey thisMonthSurvey = surveyRepository.findByYearMonthOf(thisMonthYearMonth);
        if (thisMonthSurvey == null) {
            throw new NoSuchElementException();
        }

        return thisMonthSurvey.getItems().stream()
                .map(SurveyItemInfo::from)
                .toList();
    }

    @Transactional
    public void submitResponse(Long surveyId, Long memberId, SurveyResponseRequest form) {
        Member member = memberRepository.findById(memberId).orElseThrow(null);
        Survey survey = getSurvey(surveyId);

        // 설문응답 생성
        SurveyResponse surveyResponse = form.toSurveyResponse(member, survey);
        surveyResponseRepository.save(surveyResponse);

        // 선택된 항목 저장
        List<SurveyItemSelected> surveyItemSelecteds = form.toSurveyItemSelecteds(surveyResponse, surveyItemRepository);
        surveyItemSelectedRepository.saveAll(surveyItemSelecteds);
    }

    @Transactional
    public void deleteSurveyItem(Long id) {
        SurveyItem item = surveyItemRepository.findById(id).orElseThrow(null);

        surveyItemRepository.delete(item);
    }

    @Transactional
    public void deleteAdditionalComment(Long id) {
        SurveyItemSelected selectedItem = surveyItemSelectedRepository.findById(id)
                .orElseThrow(null);
        surveyItemSelectedRepository.delete(selectedItem);
    }

    @Transactional
    public Survey createSurvey() {
        YearMonth currentYearMonth = YearMonth.now();
        YearMonth nextYearMonth = currentYearMonth.plusMonths(1);

        EmbeddableYearMonth nextMonthYearMonth = new EmbeddableYearMonth(nextYearMonth.getYear(), nextYearMonth.getMonth().getValue());
        Survey survey = surveyRepository.findByYearMonthOf(nextMonthYearMonth);

        if (survey == null) {
            survey = Survey.builder()
                    .yearMonthOf(nextMonthYearMonth)
                    .build();

            survey = surveyRepository.save(survey);
        }

        return survey;
    }

    @Transactional
    public void addItemToSurvey(SurveyItemRequest surveyItemRequest) {
        Long surveyId = surveyItemRequest.surveyId();
        String itemName = surveyItemRequest.name();

        Survey survey = surveyRepository.findById(surveyId).orElseThrow(null);

        SurveyItem newItem = SurveyItem.createItem(itemName, survey);
        survey.getItems().add(newItem);
        surveyItemRepository.save(newItem);
    }

    public List<SurveyItem> getNextMonthSurveyItems() {
        YearMonth thisMonth = YearMonth.now();
        YearMonth nextMonth = thisMonth.plusMonths(1); // 다음 달을 구합니다.

        EmbeddableYearMonth nextMonthYearMonth = new EmbeddableYearMonth(nextMonth.getYear(), nextMonth.getMonthValue());
        Survey nextMonthSurvey = surveyRepository.findByYearMonthOf(nextMonthYearMonth);

        List<SurveyItem> nextMonthSurveyItems = new ArrayList<>();
        if (nextMonthSurvey != null) {
            nextMonthSurveyItems.addAll(nextMonthSurvey.getItems());
        }

        return nextMonthSurveyItems;
    }

}
