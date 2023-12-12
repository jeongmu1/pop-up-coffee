package com.db8.popupcoffee.survey.dto.response;

import com.db8.popupcoffee.survey.domain.Survey;
import com.db8.popupcoffee.survey.domain.SurveyItem;
import com.db8.popupcoffee.survey.domain.SurveyItemSelected;

import java.util.List;

public record SurveySettingResponse(
        List<SurveyItem> nextMonthItems, List<SurveyItem> previousItems,
        List<SurveyItemSelected> additionalComments,
        Survey survey)
{
}
