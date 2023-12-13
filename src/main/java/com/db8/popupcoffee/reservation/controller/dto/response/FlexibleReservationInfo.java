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
    List<LocalDate> desiredDates
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
            .desiredDates(entity.getDesiredDates().stream().map(DesiredDate::getDate).toList())
            .build();
    }
}
