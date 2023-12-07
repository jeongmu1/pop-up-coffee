package com.db8.popupcoffee.seasonality.controller.dto.response;

import com.db8.popupcoffee.seasonality.domain.DateInfo;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record DatePriceInfo(
    LocalDate date,
    String seasonalityLevel,
    long rentalPrice) {

    public static DatePriceInfo of(DateInfo dateInfo) {
        return DatePriceInfo.builder()
            .date(dateInfo.getDate())
            .seasonalityLevel(dateInfo.getSeasonalityLevel().name())
            .rentalPrice(dateInfo.getSeasonalityLevel().calculateDailyFee())
            .build();
    }
}
