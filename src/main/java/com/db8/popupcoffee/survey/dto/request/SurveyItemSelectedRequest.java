package com.db8.popupcoffee.survey.dto.request;

import com.db8.popupcoffee.survey.domain.SurveyItemSelected;

public record SurveyItemSelectedRequest(
        String additionalComment
) {
    public SurveyItemSelected toEntity() {
        return SurveyItemSelected.builder()
                .additionalComment(additionalComment)
                .build();
    }
}
