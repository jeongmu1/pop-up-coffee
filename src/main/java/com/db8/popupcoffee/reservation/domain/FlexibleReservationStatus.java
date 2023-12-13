package com.db8.popupcoffee.reservation.domain;

import java.util.List;
import lombok.Getter;

@Getter
public enum FlexibleReservationStatus {
    RESERVATION_FIXED("확정"),
    SPACE_FIXED("공간 확정"),
    SPACE_TEMPORARY_FIXED("임시 공간 설정"),
    WAITING("대기중"),
    DEADLINE_MISSED("예약기간 만료"),
    CANCELED("취소됨");

    private final String message;

    FlexibleReservationStatus(String message) {
        this.message = message;
    }

    public static List<FlexibleReservationStatus> getStatusOverTemporaryFixed() {
        return List.of(RESERVATION_FIXED, SPACE_FIXED, SPACE_TEMPORARY_FIXED);
    }
}
