package com.db8.popupcoffee.settlement.controller.dto.request;

import com.db8.popupcoffee.global.domain.PaymentType;

public record OrderForm(
    Long memberId,
    Long totalPayment,
    Long spaceRentalId,
    PaymentType paymentType,
    int usedPoint
) {

}
