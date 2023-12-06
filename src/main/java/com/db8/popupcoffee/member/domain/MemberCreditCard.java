package com.db8.popupcoffee.member.domain;

import com.db8.popupcoffee.global.domain.BaseTimeEntity;
import com.db8.popupcoffee.global.domain.CreditCard;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCreditCard extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Embedded
    private CreditCard creditCard;
}
