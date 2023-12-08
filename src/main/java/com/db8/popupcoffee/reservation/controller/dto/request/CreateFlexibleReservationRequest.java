package com.db8.popupcoffee.reservation.controller.dto.request;

import java.time.LocalDate;
import java.util.List;

public record CreateFlexibleReservationRequest(
    Long businessTypeId,
    LocalDate availabilityStartDate,
    LocalDate availabilityEndDate,
    Long duration,
    LocalDate deadline,
    String contactManager,
    String contactPhone,
    List<LocalDate> desiredDates
) {

}
