package com.db8.popupcoffee.contract.domain;

import com.db8.popupcoffee.global.domain.BaseTimeEntity;
import com.db8.popupcoffee.merchant.domain.Merchant;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MerchantContract extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Merchant merchant;

    @Column(nullable = false)
    private LocalDate expireAt;

    @Column(nullable = false)
    private String contact; // 담당자

    @Column(nullable = false)
    private String contactPhone;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String accountBank;
}
