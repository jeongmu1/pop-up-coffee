package com.db8.popupcoffee.survey.domain;

import com.db8.popupcoffee.global.domain.BaseTimeEntity;
import com.db8.popupcoffee.global.domain.EmbeddableYearMonth;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey extends BaseTimeEntity {

    @Embedded
    private EmbeddableYearMonth yearMonthOf;

    @ToString.Exclude
    @OneToMany(mappedBy = "survey", fetch = FetchType.LAZY)
    private List<SurveyItem> items = new ArrayList<>();
}
