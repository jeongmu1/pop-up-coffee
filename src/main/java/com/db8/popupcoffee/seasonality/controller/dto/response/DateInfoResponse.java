package com.db8.popupcoffee.seasonality.controller.dto.response;

import com.db8.popupcoffee.seasonality.domain.DateInfo;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DateInfoResponse(
    LocalDate date,
    String seasonalityLevel,
    long rentalPrice,
    int availableSpaces,
    boolean rentableSpace,
    boolean holiday
) {

    public static DateInfoResponse of(DateInfo dateInfo, int availableSpaces) {
        return DateInfoResponse.builder()
            .date(dateInfo.getDate())
            .seasonalityLevel(dateInfo.getSeasonalityLevel().name())
            .rentalPrice(dateInfo.getSeasonalityLevel().calculateDailyFee(dateInfo.isHoliday()))
            .availableSpaces(availableSpaces)
            .rentableSpace(availableSpaces > 0)
            .holiday(dateInfo.isHoliday())
            .build();
    }
}
