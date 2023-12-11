package com.db8.popupcoffee.rental.domain;

import lombok.Getter;

@Getter
public enum SpaceRentalStatus {
    COMPLETED("종료"),
    CANCELED("취소"),
    BEFORE_SETTLEMENT("정산 전"),
    IN_USE("사용 중"),
    BEFORE_USE("사용 전");

    private final String message;

    SpaceRentalStatus(String message) {
        this.message = message;
    }

    public SpaceRentalStatus next() {
        return switch (this) {
            case BEFORE_USE -> IN_USE;
            case IN_USE -> BEFORE_SETTLEMENT;
            case BEFORE_SETTLEMENT -> COMPLETED;
            default -> null;
        };
    }
}
