package com.db8.popupcoffee.reservation.repository;

import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservationStatus;
import java.util.Collection;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface FlexibleReservationRepository extends CrudRepository<FlexibleReservation, Long> {

    List<FlexibleReservation> findByStatusIn(Collection<FlexibleReservationStatus> status);
}