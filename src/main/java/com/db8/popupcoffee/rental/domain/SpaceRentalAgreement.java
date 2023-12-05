package com.db8.popupcoffee.rental.domain;

import com.db8.popupcoffee.contract.domain.MerchantContract;
import com.db8.popupcoffee.global.domain.BaseTimeEntity;
import com.db8.popupcoffee.merchant.domain.BusinessType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.db8.popupcoffee.space.domain.Space;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpaceRentalAgreement extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MerchantContract merchantContract;

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

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String contactPhone;

    @Column(nullable = false)
    private String creditCartNumber;

    @Column(nullable = false)
    private String creditCartExpireAt;

    @Column(nullable = false)
    private String creditCartCvc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Space space;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpaceRentalStatus rentalStatus = SpaceRentalStatus.BEFORE_USE;
}
