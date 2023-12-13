package com.db8.popupcoffee.global.util;

import com.db8.popupcoffee.merchant.domain.Grade;
import com.db8.popupcoffee.seasonality.repository.DateInfoRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeeCalculator {

    private final DateInfoRepository dateInfoRepository;

    public long calculateRentalFee(LocalDate startDate, LocalDate endDate, Grade grade) {
        return (long) (dateInfoRepository.findByDateBetween(startDate, endDate)
            .stream()
            .mapToLong(info -> info.getSeasonalityLevel().calculateDailyFee(info.isHoliday()))
            .sum() * (100 - grade.getDiscountPercentage()) / 100);
    }

    public long calculateRentalDeposit(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate.plusDays(1L)) * Policy.DEPOSIT_PER_DAY;
    }
}
