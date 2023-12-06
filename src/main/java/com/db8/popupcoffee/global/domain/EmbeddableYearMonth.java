package com.db8.popupcoffee.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.YearMonth;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmbeddableYearMonth {

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int month;

    public YearMonth getYearMonth() {
        return YearMonth.of(year, month);
    }
}
