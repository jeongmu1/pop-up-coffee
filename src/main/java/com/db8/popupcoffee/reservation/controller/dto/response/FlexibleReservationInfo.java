package com.db8.popupcoffee.reservation.controller.dto.response;

import com.db8.popupcoffee.merchant.domain.Grade;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record FlexibleReservationInfo(
    String merchantName,
    int gradeScore,
    String grade,
    LocalDate availabilityStartDate,
    LocalDate availabilityEndDate,
    Long duration,
    String status
) {

    public static FlexibleReservationInfo from(FlexibleReservation entity) {
        Merchant merchant = entity.getMerchantContract().getMerchant();
        return FlexibleReservationInfo.builder()
            .merchantName(merchant.getName())
            .gradeScore(merchant.getGradeScore())
            .grade(Grade.from(merchant.getGradeScore()).name())
            .availabilityStartDate(entity.getAvailabilityStartDate())
            .availabilityEndDate(entity.getAvailabilityEndDate())
            .duration(entity.getDuration())
            .status(entity.getStatus().getMessage())
            .build();
    }
}
