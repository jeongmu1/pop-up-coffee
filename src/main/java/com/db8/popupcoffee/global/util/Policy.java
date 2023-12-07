package com.db8.popupcoffee.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Policy {

    public static final long DAILY_RENTAL_PRICE = 100_000;
    public static final long HIGHEST_SEASON_EXTRA_FEE = 75000;
    public static final long HIGH_SEASON_EXTRA_FEE = 50000;
    public static final long LOW_SEASON_DISCOUNT = 25000;
}
