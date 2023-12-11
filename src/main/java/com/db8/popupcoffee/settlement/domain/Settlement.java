package com.db8.popupcoffee.settlement.domain;

import com.db8.popupcoffee.global.domain.BaseTimeEntity;
import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
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

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Settlement extends BaseTimeEntity {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SpaceRentalAgreement spaceRentalAgreement;

    @Column(nullable = false)
    private long refundedDeposit;

    @Column(nullable = false)
    private long settledRevenue;

    @Builder
    public Settlement(SpaceRentalAgreement spaceRentalAgreement, long settledRevenue) {
        this.spaceRentalAgreement = spaceRentalAgreement;
        this.settledRevenue = settledRevenue;
        refundedDeposit = spaceRentalAgreement.getRemainingRentalDeposit();
    }
}
