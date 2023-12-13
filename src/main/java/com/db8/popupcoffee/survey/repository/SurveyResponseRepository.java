package com.db8.popupcoffee.survey.repository;

import com.db8.popupcoffee.survey.domain.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
    boolean existsBySurveyIdAndMemberId(Long surveyId, Long memberId);
}
