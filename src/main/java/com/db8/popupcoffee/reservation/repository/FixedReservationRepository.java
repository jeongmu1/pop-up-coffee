package com.db8.popupcoffee.reservation.repository;

import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.FixedReservationStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedReservationRepository extends CrudRepository<FixedReservation, Long>,
    FixedReservationCustomRepository {

    List<FixedReservation> findByEndDateAfterAndFromFlexibleReservation(LocalDate date,
        boolean fromFlexibleReservation);

    List<FixedReservation> findByStatusIsNot(FixedReservationStatus status);
}
