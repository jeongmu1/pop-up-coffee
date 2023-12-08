package com.db8.popupcoffee.survey.dto.request;

import com.db8.popupcoffee.global.domain.EmbeddableYearMonth;
import com.db8.popupcoffee.survey.domain.Survey;

public record SurveySettingRequest(
        int year,
        int month
) {
    public Survey toEntity() {
        return Survey.builder()
                .yearMonthOf(new EmbeddableYearMonth(year, month))
                .build();
    }
}
