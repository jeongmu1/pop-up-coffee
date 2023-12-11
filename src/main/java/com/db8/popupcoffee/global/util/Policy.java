package com.db8.popupcoffee.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Policy {

    public static final long DAILY_RENTAL_PRICE = 100_000;
    public static final long HIGHEST_SEASON_EXTRA_FEE = 75000;
    public static final long HIGH_SEASON_EXTRA_FEE = 50000;
    public static final long HOLIDAY_EXTRA_FEE = 25000;
    public static final long LOW_SEASON_DISCOUNT = 25000;
    public static final int FIRST_RENTAL_SCORE_CHANGES = 200;
    public static final int SCORE_CHANGES_PER_DAY = 5;
    public static final int REVENUE_FOR_ONE_SCORE = 5000; // 매출 만원 당 등급점수
    public static final int GREEN_MIN_SCORE = 250;
    public static final int PURPLE_MIN_SCORE = 500;
    public static final int VIP_MIN_SCORE = 1000;
    public static final int SURVEY_REWARD = 500;
    public static final double VIP_SHARE_PERCENTAGE = 10;
    public static final double PURPLE_SHARE_PERCENTAGE = 20;
    public static final double GREEN_SHARE_PERCENTAGE = 25;
    public static final double WHITE_SHARE_PERCENTAGE = 30;
    public static final long DEPOSIT_PER_DAY = 50000;
}
