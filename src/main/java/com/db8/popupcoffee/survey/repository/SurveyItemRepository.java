package com.db8.popupcoffee.survey.repository;

import com.db8.popupcoffee.survey.domain.SurveyItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyItemRepository extends JpaRepository<SurveyItem, Long> {
}
