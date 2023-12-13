package com.db8.popupcoffee.space.domain;

import com.db8.popupcoffee.global.domain.BaseEntity;
import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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
public class Space extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String number;

    @ToString.Exclude
    @OneToMany(mappedBy = "space", fetch = FetchType.LAZY)
    private List<SpaceRentalAgreement> spaceRentalAgreements = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "temporalSpace", fetch = FetchType.LAZY)
    private List<FlexibleReservation> flexibleReservations = new ArrayList<>();
}
