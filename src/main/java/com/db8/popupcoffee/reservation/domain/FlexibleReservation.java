package com.db8.popupcoffee.reservation.domain;

import com.db8.popupcoffee.contract.domain.MerchantContract;
import com.db8.popupcoffee.global.domain.BaseTimeEntity;
import com.db8.popupcoffee.merchant.domain.BusinessType;
import com.db8.popupcoffee.space.domain.Space;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
public class FlexibleReservation extends BaseTimeEntity {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MerchantContract merchantContract;

    @Column(nullable = false)
    private LocalDate availabilityStartDate;

    @Column(nullable = false)
    private LocalDate availabilityEndDate;

    private Long duration; // null 시 기간 무관

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlexibleReservationStatus status = FlexibleReservationStatus.WAITING;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Space temporalSpace;

    private LocalDate temporalRentalStartDate;

    private LocalDate temporalRentalEndDate;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private FixedReservation fixedReservation;

    @Column(nullable = false)
    private LocalDate deadline;

    @ToString.Exclude
    @OneToMany(mappedBy = "flexibleReservation", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<DesiredDate> desiredDates = new ArrayList<>();

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BusinessType businessType;

    @Builder
    public FlexibleReservation(
        MerchantContract merchantContract,
        LocalDate availabilityStartDate,
        LocalDate availabilityEndDate,
        Long duration,
        LocalDate deadline,
        BusinessType businessType
    ) {
        this.merchantContract = merchantContract;
        this.availabilityStartDate = availabilityStartDate;
        this.availabilityEndDate = availabilityEndDate;
        this.duration = duration;
        this.deadline = deadline;
        this.businessType = businessType;
    }
}
