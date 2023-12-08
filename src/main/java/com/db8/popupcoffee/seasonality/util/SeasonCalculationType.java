package com.db8.popupcoffee.seasonality.util;

@FunctionalInterface
public interface SeasonCalculationType {

    long calculateDailyFee();
}
