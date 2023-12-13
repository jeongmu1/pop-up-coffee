package com.db8.popupcoffee.space.repository;

import com.db8.popupcoffee.space.domain.Space;
import java.time.LocalDate;
import java.util.List;

public interface SpaceCustomRepository {

    List<Space> findAvailableSpaces(LocalDate startDate, LocalDate endDate);
}
