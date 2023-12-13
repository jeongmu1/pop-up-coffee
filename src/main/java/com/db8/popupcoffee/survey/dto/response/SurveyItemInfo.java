package com.db8.popupcoffee.survey.dto.response;

import com.db8.popupcoffee.survey.domain.SurveyItem;

public record SurveyItemInfo(
        Long id,
        String name,
        int selectedCount
) {

    public static SurveyItemInfo from(SurveyItem surveyItem) {
        return new SurveyItemInfo(
                surveyItem.getId(),
                surveyItem.getName(),
                surveyItem.getSurveyItemSelecteds().size()
        );
    }
}
