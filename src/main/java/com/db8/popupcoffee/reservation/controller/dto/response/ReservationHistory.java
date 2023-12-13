package com.db8.popupcoffee.reservation.controller.dto.response;

import com.db8.popupcoffee.global.util.FeeCalculator;
import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import com.db8.popupcoffee.reservation.domain.DesiredDate;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservationStatus;
import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder
public record ReservationHistory(
    Long id,
    LocalDate reservedDate,
    String status,
    LocalDate rentalStartDate,
    LocalDate rentalEndDate,
    boolean charged,
    String space, // null 시 배정 중
    String merchant,
    String businessType,
    // 결제 관련
    Long rentalFee,
    Long rentalDeposit,

    // 유동 예약 관련
    boolean flexible,
    LocalDate availableStartDate,
    LocalDate availableEndDate,
    Long duration,
    LocalDate deadline,
    List<LocalDate> desiredDates
) {

    public static ReservationHistory of(FixedReservation fixedReservation) {
        SpaceRentalAgreement rental = fixedReservation.getSpaceRentalAgreement();
        return ReservationHistory.builder()
            .id(fixedReservation.getId())
            .reservedDate(fixedReservation.getCreatedAt().toLocalDate())
            .status(fixedReservation.getStatus().getMessage())
            .rentalStartDate(fixedReservation.getStartDate())
            .rentalEndDate(fixedReservation.getEndDate())
            .rentalFee(rental != null ? rental.getRentalFee() : null)
            .rentalDeposit(rental != null ? rental.getRentalDeposit() : null)
            .charged(true)
            .space(rental != null ? rental.getSpace().getNumber() : null)
            .flexible(false)
            .merchant(fixedReservation.getMerchantContract().getMerchant().getName())
            .businessType(fixedReservation.getMerchantContract().getMerchant().getBusinessType()
                .getName())
            .build();
    }

    public static ReservationHistory of(FlexibleReservation flexibleReservation,
        FeeCalculator feeCalculator) {
        ReservationHistoryBuilder historyBuilder = ReservationHistory.builder();
        historyBuilder.reservedDate(flexibleReservation.getCreatedAt().toLocalDate())
            .id(flexibleReservation.getId())
            .status(flexibleReservation.getStatus().getMessage())
            .flexible(true)
            .availableStartDate(flexibleReservation.getAvailabilityStartDate())
            .availableEndDate(flexibleReservation.getAvailabilityEndDate())
            .merchant(flexibleReservation.getMerchantContract().getMerchant().getName())
            .businessType(flexibleReservation.getMerchantContract().getMerchant().getBusinessType()
                .getName())
                .duration(flexibleReservation.getDuration())
                .deadline(flexibleReservation.getDeadline())
                .desiredDates(flexibleReservation.getDesiredDates().stream().map(DesiredDate::getDate).toList())
            .charged(false);

        FlexibleReservationStatus status = flexibleReservation.getStatus();
        if (status.equals(FlexibleReservationStatus.SPACE_FIXED)) {
            historyBuilder.rentalDeposit(feeCalculator.calculateRentalDeposit(
                    flexibleReservation.getTemporalRentalStartDate(),
                    flexibleReservation.getTemporalRentalEndDate()))
                .rentalFee(feeCalculator.calculateRentalFee(
                    flexibleReservation.getTemporalRentalStartDate(),
                    flexibleReservation.getTemporalRentalEndDate()))
                .space(flexibleReservation.getTemporalSpace().getNumber())
                .rentalStartDate(flexibleReservation.getTemporalRentalStartDate())
                .rentalEndDate(flexibleReservation.getTemporalRentalEndDate());
        }

        if (status.equals(FlexibleReservationStatus.RESERVATION_FIXED)
            && flexibleReservation.getFixedReservation() != null) {

            FixedReservation fixedReservation = flexibleReservation.getFixedReservation();
            SpaceRentalAgreement rental = fixedReservation.getSpaceRentalAgreement();
            historyBuilder.rentalStartDate(fixedReservation.getStartDate())
                .rentalEndDate(fixedReservation.getEndDate())
                .charged(true)
                .space(rental.getSpace().getNumber());
        }

        return historyBuilder.build();
    }

}
