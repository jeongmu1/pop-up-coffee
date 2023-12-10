package com.db8.popupcoffee.reservation.controller.dto.response;

import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservationStatus;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ReservationHistory(
    LocalDate reservedDate,
    String status,
    LocalDate rentalStartDate,
    LocalDate rentalEndDate,
    boolean charged,
    String space, // null 시 배정 중

    // 유동 예약 관련
    boolean flexible,
    LocalDate availableStartDate,
    LocalDate availableEndDate
) {

    public static ReservationHistory from(FixedReservation fixedReservation) {
        return ReservationHistory.builder()
            .reservedDate(fixedReservation.getCreatedAt().toLocalDate())
            .status(fixedReservation.getStatus().name())
            .rentalStartDate(fixedReservation.getStartDate())
            .rentalEndDate(fixedReservation.getEndDate())
            .charged(true)
            .space(fixedReservation.getTemporalSpace() == null ? null
                : fixedReservation.getTemporalSpace().getNumber())
            .flexible(false)
            .build();
    }

    public static ReservationHistory from(FlexibleReservation flexibleReservation) {
        ReservationHistoryBuilder historyBuilder = ReservationHistory.builder();
        historyBuilder.reservedDate(flexibleReservation.getCreatedAt().toLocalDate())
            .status(flexibleReservation.getStatus().name())
            .flexible(true)
            .availableStartDate(flexibleReservation.getAvailabilityStartDate())
            .availableEndDate(flexibleReservation.getAvailabilityEndDate());

        FlexibleReservationStatus status = flexibleReservation.getStatus();

        if (status.equals(FlexibleReservationStatus.RESERVATION_FIXED) || status.equals(
            FlexibleReservationStatus.SPACE_FIXED)
            && flexibleReservation.getFixedReservation() != null) {

            FixedReservation fixedReservation = flexibleReservation.getFixedReservation();
            historyBuilder.rentalStartDate(fixedReservation.getStartDate())
                .rentalEndDate(fixedReservation.getEndDate())
                .charged(status.equals(FlexibleReservationStatus.RESERVATION_FIXED))
                .space(fixedReservation.getTemporalSpace().getNumber());
        }

        return historyBuilder.build();
    }

}
