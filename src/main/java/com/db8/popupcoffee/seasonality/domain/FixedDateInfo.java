package com.db8.popupcoffee.seasonality.domain;

import com.db8.popupcoffee.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixedDateInfo extends BaseEntity {

    @Column(nullable = false)
    private short month;

    @Column(nullable = false)
    private short day;

    @Column(nullable = false)
    private boolean lunar = false; // 음력 여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeasonalityLevel seasonalityLevel;
}
