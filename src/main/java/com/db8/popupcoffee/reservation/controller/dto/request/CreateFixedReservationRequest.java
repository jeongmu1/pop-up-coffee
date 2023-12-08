package com.db8.popupcoffee.reservation.controller.dto.request;

import java.time.LocalDate;

public record CreateFixedReservationRequest(
    LocalDate startDate,
    LocalDate endDate,
    String contactManager,
    String contactPhone,
    String creditCardNumber,
    String creditCardExpireAt,
    String creditCardCvc
) {

}
