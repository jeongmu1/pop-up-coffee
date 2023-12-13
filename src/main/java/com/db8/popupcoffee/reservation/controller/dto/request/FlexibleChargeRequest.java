package com.db8.popupcoffee.reservation.controller.dto.request;

public record FlexibleChargeRequest(
    Long reservationId,
    String ccNumber,
    String ccExpiration,
    String ccCvc
) {

}
