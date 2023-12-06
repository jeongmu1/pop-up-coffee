package com.db8.popupcoffee.global.domain;

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
public class CreditCard {

    @Column(nullable = false)
    private String creditCardNumber;

    @Column(nullable = false)
    private String creditCardExpireAt;

    @Column(nullable = false)
    private String creditCardCvc;
}
