package com.db8.popupcoffee.seasonality.controller.dto.response;

import com.db8.popupcoffee.seasonality.domain.SeasonalityLevel;
import java.util.Arrays;
import java.util.List;

public record SimpleSeasonalityLevel(
    String name,
    String explain
) {

    public static List<SimpleSeasonalityLevel> simpleSeasonalityLevels() {
        return Arrays.stream(SeasonalityLevel.values()).map(SimpleSeasonalityLevel::from).toList();
    }

    private static SimpleSeasonalityLevel from(SeasonalityLevel seasonalityLevel) {
        return new SimpleSeasonalityLevel(seasonalityLevel.name(), seasonalityLevel.getExplain());
    }
}
