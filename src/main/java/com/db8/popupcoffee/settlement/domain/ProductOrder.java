package com.db8.popupcoffee.settlement.domain;

import com.db8.popupcoffee.global.domain.BaseOrderEntity;
import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrder extends BaseOrderEntity {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SpaceRentalAgreement spaceRentalAgreement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductOrderStatus status;

    @Column(nullable = false)
    private long totalPayment;

    private String refundReason;
}
