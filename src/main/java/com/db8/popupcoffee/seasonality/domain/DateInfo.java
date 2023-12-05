package com.db8.popupcoffee.seasonality.domain;

import com.db8.popupcoffee.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateInfo extends BaseEntity {

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeasonalityLevel seasonalityLevel;

    @Column(nullable = false)
    private boolean holiday;
}
