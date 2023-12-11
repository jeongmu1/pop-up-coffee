package com.db8.popupcoffee.rental.controller.dto.response;

import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SimpleRentalInfo(
    long id,
    String merchant,
    LocalDate startDate,
    LocalDate endDate,
    double revenueSharePercentage,
    String businessType,
    LocalDateTime createdAt,
    long deposit,
    long remainingDeposit,
    long rentalFee,
    String space,
    String status
) {

    public static SimpleRentalInfo from(SpaceRentalAgreement spaceRentalAgreement) {
        return SimpleRentalInfo.builder()
            .id(spaceRentalAgreement.getId())
            .merchant(spaceRentalAgreement.getMerchantContract().getMerchant().getName())
            .startDate(spaceRentalAgreement.getRentalDuration().getStartDate())
            .endDate(spaceRentalAgreement.getRentalDuration().getEndDate())
            .revenueSharePercentage(spaceRentalAgreement.getRevenueSharePercentage())
            .businessType(spaceRentalAgreement.getBusinessType().getName())
            .createdAt(spaceRentalAgreement.getCreatedAt())
            .deposit(spaceRentalAgreement.getRentalDeposit())
            .remainingDeposit(spaceRentalAgreement.getRemainingRentalDeposit())
            .rentalFee(spaceRentalAgreement.getRentalFee())
            .space(spaceRentalAgreement.getSpace().getNumber())
            .status(spaceRentalAgreement.getRentalStatus().getMessage())
            .build();
    }
}
