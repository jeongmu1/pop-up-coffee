package com.db8.popupcoffee.survey.domain;

import com.db8.popupcoffee.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyItem extends BaseTimeEntity {

    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Survey survey;

    @ToString.Exclude
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<SurveyItemSelected> surveyItemSelecteds = new ArrayList<>();

    public static SurveyItem createItem(String name, Survey survey) {
        SurveyItem item = new SurveyItem();
        item.setName(name);
        item.setSurvey(survey);
        return item;
    }
}
