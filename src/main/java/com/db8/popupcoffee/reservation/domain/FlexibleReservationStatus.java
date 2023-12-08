package com.db8.popupcoffee.reservation.domain;

import lombok.Getter;

@Getter
public enum FlexibleReservationStatus {
    RESERVATION_FIXED("예약 확정"),
    SPACE_FIXED("공간 확정"),
    SPACE_TEMPORARY_FIXED("임시 공간 설정"),
    DEADLINE_MISSED("예약기간 만료"),
    WAITING("대기중");

    private final String message;

    FlexibleReservationStatus(String message) {
        this.message = message;
    }
}
