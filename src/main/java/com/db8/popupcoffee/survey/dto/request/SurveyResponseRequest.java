package com.db8.popupcoffee.survey.dto.request;

import com.db8.popupcoffee.member.domain.Member;
import com.db8.popupcoffee.survey.domain.Survey;
import com.db8.popupcoffee.survey.domain.SurveyItem;
import com.db8.popupcoffee.survey.domain.SurveyItemSelected;
import com.db8.popupcoffee.survey.domain.SurveyResponse;
import com.db8.popupcoffee.survey.repository.SurveyItemRepository;

import java.util.List;
import java.util.stream.Collectors;

public record SurveyResponseRequest(
        List<Long> selectedItems,
        String additionalComment
) {
    public SurveyResponse toSurveyResponse(Member member, Survey survey) {
        return SurveyResponse.builder()
                .member(member)
                .survey(survey)
                .build();
    }

    public List<SurveyItemSelected> toSurveyItemSelecteds(SurveyResponse surveyResponse, SurveyItemRepository surveyItemRepository) {
        return this.selectedItems.stream()
                .map(itemId -> {
                    SurveyItem item = surveyItemRepository.findById(itemId).orElseThrow(null);
                    return SurveyItemSelected.builder()
                            .item(item)
                            .surveyResponse(surveyResponse)
                            .additionalComment(this.additionalComment)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
