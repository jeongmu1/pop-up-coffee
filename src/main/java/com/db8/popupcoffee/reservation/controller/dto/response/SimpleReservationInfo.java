package com.db8.popupcoffee.reservation.controller.dto.response;

import com.db8.popupcoffee.reservation.domain.DesiredDate;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservationStatus;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder
public record SimpleReservationInfo(
        LocalDate startDate,
        LocalDate endDate,
        String merchantName,
        Long id,

        // 유동 관련
        boolean fromFlexible,
        Boolean fixedDate, // null 시 Fixed
        Long rentalDuration, // null 시 무관n
        List<LocalDate> desiredDates
) {

    public static SimpleReservationInfo from(FlexibleReservation flexibleReservation) {
        FlexibleReservationStatus status = flexibleReservation.getStatus();
        return SimpleReservationInfo.builder()
                .startDate(flexibleReservation.getTemporalRentalStartDate())
                .endDate(flexibleReservation.getTemporalRentalEndDate())
                .merchantName(flexibleReservation.getMerchantContract().getMerchant().getName())
                .fromFlexible(true)
                .fixedDate(status.equals(FlexibleReservationStatus.SPACE_FIXED) || status.equals(
                        FlexibleReservationStatus.RESERVATION_FIXED))
                .rentalDuration(flexibleReservation.getDuration())
                .desiredDates(flexibleReservation.getDesiredDates().stream().map(DesiredDate::getDate).toList())
                .id(flexibleReservation.getId())
                .build();
    }

    public static SimpleReservationInfo from(FixedReservation fixedReservation) {
        return SimpleReservationInfo.builder()
                .startDate(fixedReservation.getStartDate())
                .endDate(fixedReservation.getEndDate())
                .merchantName(fixedReservation.getMerchantContract().getMerchant().getName())
                .fromFlexible(false)
                .id(fixedReservation.getId())
                .build();
    }
}
