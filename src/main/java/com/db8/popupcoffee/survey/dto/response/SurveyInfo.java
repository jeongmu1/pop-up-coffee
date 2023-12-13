package com.db8.popupcoffee.survey.dto.response;

import com.db8.popupcoffee.global.domain.EmbeddableYearMonth;
import com.db8.popupcoffee.survey.domain.Survey;

public record SurveyInfo(
    long id,
    int year,
    int month
) {

    public static SurveyInfo from(Survey survey) {
        EmbeddableYearMonth yearMonth = survey.getYearMonthOf();
        return new SurveyInfo(
            survey.getId(),
            yearMonth.getYear(),
            yearMonth.getMonth()
        );
    }
}
