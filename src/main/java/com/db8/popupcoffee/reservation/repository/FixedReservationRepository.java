package com.db8.popupcoffee.reservation.repository;

import com.db8.popupcoffee.reservation.domain.FixedReservation;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface FixedReservationRepository extends CrudRepository<FixedReservation, Long> {

    List<FixedReservation> findByEndDateAfterAndFromFlexibleReservation(LocalDate date,
        boolean fromFlexibleReservation);
}
