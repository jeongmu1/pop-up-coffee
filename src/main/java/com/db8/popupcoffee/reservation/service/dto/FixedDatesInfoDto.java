package com.db8.popupcoffee.reservation.service.dto;

import java.time.LocalDate;

public record FixedDatesInfoDto(
    long merchantId,
    LocalDate start,
    LocalDate end
) {

}
