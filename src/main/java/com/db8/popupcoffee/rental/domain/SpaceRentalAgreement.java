package com.db8.popupcoffee.rental.domain;

import com.db8.popupcoffee.contract.domain.MerchantContract;
import com.db8.popupcoffee.global.domain.BaseTimeEntity;
import com.db8.popupcoffee.global.domain.Contact;
import com.db8.popupcoffee.global.domain.CreditCard;
import com.db8.popupcoffee.merchant.domain.BusinessType;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.db8.popupcoffee.space.domain.Space;
import lombok.Setter;
import lombok.ToString;

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
    private long remainingRentalDeposit;

    @Embedded
    private Duration rentalDuration;

    @Embedded
    private Contact contact;

    @Embedded
    private CreditCard creditCard;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Space space;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpaceRentalStatus rentalStatus = SpaceRentalStatus.BEFORE_USE;
}
