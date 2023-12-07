package com.db8.popupcoffee.survey.dto.request;

import com.db8.popupcoffee.global.domain.EmbeddableYearMonth;
import com.db8.popupcoffee.survey.domain.Survey;

import java.util.List;

public record SurveySettingRequest(
        int year,
        int month,
        List<Long> selectedItems
) {
    public Survey toEntity() {
        return Survey.builder()
                .yearMonthOf(new EmbeddableYearMonth(year, month))
                .build();
    }
}
