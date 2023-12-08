package com.db8.popupcoffee.seasonality.domain;

import static com.db8.popupcoffee.global.util.Policy.*;

import com.db8.popupcoffee.seasonality.util.SeasonCalculationType;
import lombok.Getter;

public enum SeasonalityLevel {

    HIGHEST(() -> HIGHEST_SEASON_EXTRA_FEE + DAILY_RENTAL_PRICE, "최성수기"),
    HIGH(() -> HIGH_SEASON_EXTRA_FEE + DAILY_RENTAL_PRICE, "성수기"),
    NORMAL(() -> DAILY_RENTAL_PRICE, "일반"),
    LOW(() -> DAILY_RENTAL_PRICE - LOW_SEASON_DISCOUNT, "비수기");

    SeasonalityLevel(SeasonCalculationType seasonCalculationType, String explain) {
        this.calculation = seasonCalculationType;
        this.explain = explain;
    }

    private final SeasonCalculationType calculation;
    @Getter
    private final String explain;

    public long calculateDailyFee() {
        return calculation.calculateDailyFee();
    }
}
