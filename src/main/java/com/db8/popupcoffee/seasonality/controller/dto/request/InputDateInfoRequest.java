package com.db8.popupcoffee.seasonality.controller.dto.request;

import com.db8.popupcoffee.seasonality.domain.SeasonalityLevel;
import java.time.LocalDate;

public record InputDateInfoRequest(
    SeasonalityLevel seasonalityLevel,
    LocalDate startDate,
    LocalDate endDate,
    Boolean holiday
) {

}
