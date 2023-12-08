package com.db8.popupcoffee.reservation.repository;

import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservationStatus;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlexibleReservationRepository extends CrudRepository<FlexibleReservation, Long>,
    FlexibleReservationCustomRepository {

    List<FlexibleReservation> findByStatusIn(Collection<FlexibleReservationStatus> status);

    List<FlexibleReservation> findByTemporalRentalEndDateAfterAndStatusIn(LocalDate date,
        Collection<FlexibleReservationStatus> status);
}