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
    Long id,
    String contactManager,
    String contactPhone,
    String businessType,
    String status,

    // 유동 관련
    boolean fromFlexible,
    Boolean fixedDate, // null 시 Fixed
    Long rentalDuration // null 시 무관n,
) {

    public static SimpleReservationInfo from(FlexibleReservation flexibleReservation) {
        FlexibleReservationStatus status = flexibleReservation.getStatus();
        var contact = flexibleReservation.getMerchantContract().getContact();
        return SimpleReservationInfo.builder()
            .startDate(flexibleReservation.getTemporalRentalStartDate())
            .endDate(flexibleReservation.getTemporalRentalEndDate())
            .merchantName(flexibleReservation.getMerchantContract().getMerchant().getName())
            .fromFlexible(true)
            .fixedDate(status.equals(FlexibleReservationStatus.SPACE_FIXED) || status.equals(
                FlexibleReservationStatus.RESERVATION_FIXED))
            .rentalDuration(flexibleReservation.getDuration())
            .id(flexibleReservation.getId())
            .contactManager(contact.getContactManager())
            .contactPhone(contact.getContactPhone())
            .businessType(flexibleReservation.getBusinessType().getName())
            .status(flexibleReservation.getStatus().getMessage())
            .build();
    }

    public static SimpleReservationInfo from(FixedReservation fixedReservation) {
        var contact = fixedReservation.getMerchantContract().getContact();
        return SimpleReservationInfo.builder()
            .startDate(fixedReservation.getStartDate())
            .endDate(fixedReservation.getEndDate())
            .merchantName(fixedReservation.getMerchantContract().getMerchant().getName())
            .fromFlexible(false)
            .id(fixedReservation.getId())
            .contactManager(contact.getContactManager())
            .contactPhone(contact.getContactPhone())
            .businessType(fixedReservation.getBusinessType().getName())
            .status(fixedReservation.getStatus().getMessage())
            .build();
    }
}
