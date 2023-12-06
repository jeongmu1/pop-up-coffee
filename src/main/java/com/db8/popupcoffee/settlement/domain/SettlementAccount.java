package com.db8.popupcoffee.settlement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SettlementAccount {

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String accountBank;

    @Column(nullable = false)
    private String accountOwner;
}
