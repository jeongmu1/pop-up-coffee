package com.db8.popupcoffee.seasonality.domain;

import com.db8.popupcoffee.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DateInfo extends BaseEntity {

    @Column(nullable = false, unique = true)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeasonalityLevel seasonalityLevel;

    @Column(nullable = false)
    private boolean holiday;

    public DateInfo(LocalDate date) {
        this.date = date;
        this.seasonalityLevel = SeasonalityLevel.NORMAL;
        this.holiday = isWeekend(date);
    }

    private boolean isWeekend(LocalDate date) {
        List<DayOfWeek> weekends = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        return weekends.stream().anyMatch(date.getDayOfWeek()::equals);
    }
}
