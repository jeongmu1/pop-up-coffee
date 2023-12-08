package com.db8.popupcoffee.survey.repository;

import com.db8.popupcoffee.global.domain.EmbeddableYearMonth;
import com.db8.popupcoffee.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    Survey findByYearMonthOf(EmbeddableYearMonth yearMonth);
}
