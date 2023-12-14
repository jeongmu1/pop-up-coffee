package com.db8.popupcoffee.contract.domain;

import com.db8.popupcoffee.global.domain.BaseTimeEntity;
import com.db8.popupcoffee.global.domain.Contact;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import com.db8.popupcoffee.settlement.domain.SettlementAccount;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Builder
public class MerchantContract extends BaseTimeEntity {

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Merchant merchant;

    @Column(nullable = false)
    private LocalDate expireAt;

    @Embedded
    private Contact contact;

    @Embedded
    private SettlementAccount settlementAccount;

    @ToString.Exclude
    @OneToMany(mappedBy = "merchantContract", fetch = FetchType.LAZY)
    private List<SpaceRentalAgreement> spaceRentalAgreements = new ArrayList<>();
}
