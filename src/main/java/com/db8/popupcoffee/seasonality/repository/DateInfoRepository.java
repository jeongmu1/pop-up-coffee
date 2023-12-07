package com.db8.popupcoffee.seasonality.repository;

import com.db8.popupcoffee.seasonality.domain.DateInfo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface DateInfoRepository extends CrudRepository<DateInfo, Long> {

    List<DateInfo> findByDateBetween(LocalDate startDate, LocalDate endDate);
}