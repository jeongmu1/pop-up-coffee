package com.db8.popupcoffee.reservation.controller.dto.response;

import com.db8.popupcoffee.merchant.domain.Grade;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.reservation.domain.DesiredDate;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder
public record FlexibleReservationInfo(
        String merchantName,
        int gradeScore,
        String grade,
        LocalDate availabilityStartDate,
        LocalDate availabilityEndDate,
        Long duration,
        String status,
        LocalDate deadline,
        List<LocalDate> desiredDates,
        Long id,
        String contactPhone,
        String contactManager,
        LocalDate tempStartDate,
        LocalDate tempEndDate,
        String businessType,
        String space
) {

    public static FlexibleReservationInfo from(FlexibleReservation entity) {
        Merchant merchant = entity.getMerchantContract().getMerchant();
        var contact = entity.getMerchantContract().getContact();
        return FlexibleReservationInfo.builder()
                .merchantName(merchant.getName())
                .gradeScore(merchant.getGradeScore())
                .grade(Grade.from(merchant.getGradeScore()).name())
                .availabilityStartDate(entity.getAvailabilityStartDate())
                .availabilityEndDate(entity.getAvailabilityEndDate())
                .duration(entity.getDuration())
                .status(entity.getStatus().getMessage())
                .deadline(entity.getDeadline())
                .desiredDates(entity.getDesiredDates().stream().map(DesiredDate::getDate).toList())
                .id(entity.getId())
                .contactManager(contact.getContactManager())
                .contactPhone(contact.getContactPhone())
                .tempStartDate(entity.getTemporalRentalStartDate())
                .tempEndDate(entity.getTemporalRentalEndDate())
                .businessType(entity.getBusinessType().getName())
                .space(entity.getTemporalSpace() != null ? entity.getTemporalSpace().getNumber() : null)
                .build();
    }
}
