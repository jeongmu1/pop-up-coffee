package com.db8.popupcoffee.survey.dto.request;

import com.db8.popupcoffee.member.domain.Member;
import com.db8.popupcoffee.survey.domain.Survey;
import com.db8.popupcoffee.survey.domain.SurveyItem;
import com.db8.popupcoffee.survey.domain.SurveyItemSelected;
import com.db8.popupcoffee.survey.domain.SurveyResponse;
import com.db8.popupcoffee.survey.repository.SurveyItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record SurveyResponseRequest(
        String additionalComment,
        List<Long> selectedItems
) {
    public SurveyResponse toSurveyResponse(Member member, Survey survey) {
        return SurveyResponse.builder()
                .member(member)
                .survey(survey)
                .build();
    }

    public List<SurveyItemSelected> toSurveyItemSelecteds(SurveyResponse surveyResponse, SurveyItemRepository surveyItemRepository) {
        if (this.selectedItems == null) {
            return new ArrayList<>();
        }

        return selectedItems.stream()
                .map(itemId -> {
                    SurveyItem item = surveyItemRepository.findById(itemId).orElseThrow(null);
                    SurveyItemSelected surveyItemSelected = SurveyItemSelected.builder()
                            .item(item)
                            .surveyResponse(surveyResponse)
                            .build();

                    if ("기타".equals(item.getName())) {
                        surveyItemSelected.setAdditionalComment(this.additionalComment);
                    }

                    return surveyItemSelected;
                })
                .collect(Collectors.toList());
    }


}

