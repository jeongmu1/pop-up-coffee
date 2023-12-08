package com.db8.popupcoffee.seasonality.repository;

import com.db8.popupcoffee.seasonality.domain.DateInfo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;

public interface DateInfoRepository extends ListCrudRepository<DateInfo, Long> {

    List<DateInfo> findByDateBetween(LocalDate startDate, LocalDate endDate);

    void deleteByDateBetween(LocalDate start, LocalDate end);
}
