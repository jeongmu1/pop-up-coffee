package com.db8.popupcoffee.seasonality.domain;

import static com.db8.popupcoffee.global.util.Policy.*;

import com.db8.popupcoffee.seasonality.util.SeasonCalculationType;

public enum SeasonalityLevel {

    HIGHEST(() -> HIGHEST_SEASON_EXTRA_FEE + DAILY_RENTAL_PRICE),
    HIGH(() -> HIGH_SEASON_EXTRA_FEE + DAILY_RENTAL_PRICE),
    NORMAL(() -> DAILY_RENTAL_PRICE),
    LOW(() -> DAILY_RENTAL_PRICE - LOW_SEASON_DISCOUNT);

    SeasonalityLevel(SeasonCalculationType seasonCalculationType) {
        this.calculation = seasonCalculationType;
    }

    private final SeasonCalculationType calculation;

    public long calculateDailyFee() {
        return calculation.calculateDailyFee();
    }
}
