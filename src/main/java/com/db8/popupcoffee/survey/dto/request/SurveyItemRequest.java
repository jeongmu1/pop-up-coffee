package com.db8.popupcoffee.survey.dto.request;

import com.db8.popupcoffee.survey.domain.SurveyItem;


public record SurveyItemRequest(
        String name
) {
    public SurveyItem toEntity() {
        return SurveyItem.builder()
                .name(name)
                .build();
    }
}
