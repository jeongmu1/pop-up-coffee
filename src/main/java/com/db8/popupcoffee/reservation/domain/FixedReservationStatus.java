package com.db8.popupcoffee.reservation.domain;

import lombok.Getter;

@Getter
public enum FixedReservationStatus {

    FIXED("확정"),
    CANCELED("취소됨");

    private final String message;

    FixedReservationStatus(String message) {
        this.message = message;
    }
}
