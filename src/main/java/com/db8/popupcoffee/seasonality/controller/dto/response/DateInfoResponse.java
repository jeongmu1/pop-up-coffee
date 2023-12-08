package com.db8.popupcoffee.seasonality.controller.dto.response;

import com.db8.popupcoffee.seasonality.domain.DateInfo;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record DateInfoResponse(
    LocalDate date,
    String seasonalityLevel,
    long rentalPrice,
    int availableSpaces,
    boolean rentableSpace
) {

    public static DateInfoResponse of(DateInfo dateInfo, int availableSpaces) {
        return DateInfoResponse.builder()
            .date(dateInfo.getDate())
            .seasonalityLevel(dateInfo.getSeasonalityLevel().name())
            .rentalPrice(dateInfo.getSeasonalityLevel().calculateDailyFee())
            .availableSpaces(availableSpaces)
            .rentableSpace(availableSpaces > 0)
            .build();
    }
}
