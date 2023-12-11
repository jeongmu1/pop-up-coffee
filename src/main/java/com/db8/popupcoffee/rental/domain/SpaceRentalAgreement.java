package com.db8.popupcoffee.rental.domain;

import com.db8.popupcoffee.contract.domain.MerchantContract;
import com.db8.popupcoffee.global.domain.BaseTimeEntity;
import com.db8.popupcoffee.global.domain.CreditCard;
import com.db8.popupcoffee.merchant.domain.BusinessType;
import com.db8.popupcoffee.merchant.domain.Grade;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.space.domain.Space;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static com.db8.popupcoffee.global.util.Policy.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpaceRentalAgreement extends BaseTimeEntity {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MerchantContract merchantContract;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BusinessType businessType;

    @Column(nullable = false)
    private Double revenueSharePercentage;

    @Column(nullable = false)
    private long rentalFee;

    @Column(nullable = false)
    private long rentalDeposit;

    @Column(nullable = false)
    private long remainingRentalDeposit = rentalDeposit;

    @Embedded
    private Duration rentalDuration;

    @Embedded
    private CreditCard creditCard;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Space space;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpaceRentalStatus rentalStatus = SpaceRentalStatus.BEFORE_USE;

    @Builder
    @SuppressWarnings("java:S107")
    public SpaceRentalAgreement(MerchantContract merchantContract, BusinessType businessType,
        Double revenueSharePercentage, long rentalFee, long rentalDeposit,
        Duration rentalDuration, CreditCard creditCard, Space space,
        SpaceRentalStatus rentalStatus) {
        this.merchantContract = merchantContract;
        this.businessType = businessType;
        this.revenueSharePercentage = revenueSharePercentage;
        this.rentalFee = rentalFee;
        this.rentalDeposit = rentalDeposit;
        this.rentalDuration = rentalDuration;
        this.creditCard = creditCard;
        this.space = space;
        this.rentalStatus = rentalStatus;
    }

    public static SpaceRentalAgreement of(FixedReservation fixedReservation, long rentalFee) {
        long durationDays = java.time.Duration.between(fixedReservation.getStartDate(),
            fixedReservation.getEndDate()).toDays();
        long deposit = durationDays * DEPOSIT_PER_DAY;
        return SpaceRentalAgreement.builder()
            .merchantContract(fixedReservation.getMerchantContract())
            .businessType(fixedReservation.getBusinessType())
            .revenueSharePercentage(
                Grade.from(fixedReservation.getMerchantContract().getMerchant().getGradeScore())
                    .getSharingPercentage())
            .rentalFee(rentalFee)
            .rentalDeposit(deposit)
            .rentalDuration(
                new Duration(fixedReservation.getStartDate(), fixedReservation.getEndDate()))
            .creditCard(fixedReservation.getCreditCard())
            .space(fixedReservation.getTemporalSpace())
            .rentalStatus(SpaceRentalStatus.BEFORE_USE)
            .build();
    }
}
