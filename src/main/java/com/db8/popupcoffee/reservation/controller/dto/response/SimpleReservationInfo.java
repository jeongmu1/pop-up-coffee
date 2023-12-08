package com.db8.popupcoffee.reservation.controller.dto.response;

import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservationStatus;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record SimpleReservationInfo(
    LocalDate startDate,
    LocalDate endDate,
    String merchantName,
    String contactManager,
    String contactPhone,

    // 유동 관련
    boolean fromFlexible,
    Boolean fixedDate, // null 시 Fixed
    Long rentalDuration // null 시 무관n
) {

    public static SimpleReservationInfo from(FlexibleReservation flexibleReservation) {
        FlexibleReservationStatus status = flexibleReservation.getStatus();
        return SimpleReservationInfo.builder()
            .startDate(flexibleReservation.getTemporalRentalStartDate())
            .endDate(flexibleReservation.getTemporalRentalEndDate())
            .merchantName(flexibleReservation.getMerchantContract().getMerchant().getName())
            .contactManager(flexibleReservation.getContact().getContactManager())
            .contactPhone(flexibleReservation.getContact().getContactPhone())
            .fromFlexible(true)
            .fixedDate(status.equals(FlexibleReservationStatus.SPACE_FIXED) || status.equals(
                FlexibleReservationStatus.RESERVATION_FIXED))
            .rentalDuration(flexibleReservation.getDuration())
            .build();
    }

    public static SimpleReservationInfo from(FixedReservation fixedReservation) {
        return SimpleReservationInfo.builder()
            .startDate(fixedReservation.getStartDate())
            .endDate(fixedReservation.getEndDate())
            .merchantName(fixedReservation.getMerchantContract().getMerchant().getName())
            .contactManager(fixedReservation.getContact().getContactManager())
            .contactPhone(fixedReservation.getContact().getContactPhone())
            .fromFlexible(false)
            .build();
    }
}
