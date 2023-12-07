package com.db8.popupcoffee.reservation.controller.dto.request;

import java.time.LocalDate;

public record CreateFlexibleReservationRequest(
    LocalDate availabilityStartDate,
    LocalDate availabilityEndDate,
    Long duration,
    LocalDate deadline,
    String contactManager,
    String contactPhone
) {

}
