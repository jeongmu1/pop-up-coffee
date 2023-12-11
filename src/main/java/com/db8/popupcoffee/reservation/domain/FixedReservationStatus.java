package com.db8.popupcoffee.reservation.domain;

import lombok.Getter;

@Getter
public enum FixedReservationStatus {

    FIXED("확정"),
    SPACE_FIXED("공간 확정"),
    SPACE_TEMPORARY_FIXED("공간 임시 확정"),
    SPACE_AWAITING("공간 배정 대기중");

    private final String message;

    FixedReservationStatus(String message) {
        this.message = message;
    }
}
