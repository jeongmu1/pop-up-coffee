package com.db8.popupcoffee.global.util;

import com.db8.popupcoffee.seasonality.repository.DateInfoRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeeCalculator {

    private final DateInfoRepository dateInfoRepository;

    public long calculateRentalFee(LocalDate startDay, LocalDate endDay) {
        return dateInfoRepository.findByDateBetween(startDay, endDay)
            .stream()
            .mapToLong(info -> info.getSeasonalityLevel().calculateDailyFee())
            .sum();
    }
}
