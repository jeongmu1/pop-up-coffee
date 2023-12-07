package com.db8.popupcoffee.reservation.repository;

import com.db8.popupcoffee.reservation.domain.FixedReservation;
import org.springframework.data.repository.CrudRepository;

public interface FixedReservationRepository extends CrudRepository<FixedReservation, Long> {

}
