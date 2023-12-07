package com.db8.popupcoffee.reservation.service.dto;

import com.db8.popupcoffee.contract.domain.MerchantContract;
import com.db8.popupcoffee.global.domain.Contact;
import com.db8.popupcoffee.global.domain.CreditCard;
import com.db8.popupcoffee.reservation.controller.dto.request.CreateFixedReservationRequest;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record CreateFixedReservationDto(
    long merchantId,
    LocalDate startDate,
    LocalDate endDate,
    Contact contact,
    CreditCard creditCard
) {

    public static CreateFixedReservationDto of(long merchantId,
        CreateFixedReservationRequest request) {
        return CreateFixedReservationDto.builder()
            .merchantId(merchantId)
            .startDate(request.startDate())
            .endDate(request.endDate())
            .contact(Contact.builder().contactManager(request.contactManager()).contactPhone(
                request.contactPhone()).build())
            .creditCard(CreditCard.builder().creditCardNumber(request.creditCardNumber())
                .creditCardExpireAt(
                    request.creditCardExpireAt()).creditCardCvc(request.creditCardCvc()).build())
            .build();
    }

    public FixedReservation toEntity(MerchantContract contract, long paymentAmount) {
        return FixedReservation.builder()
            .merchantContract(contract)
            .startDate(startDate)
            .endDate(endDate)
            .paymentAmount(paymentAmount)
            .contact(contact)
            .creditCard(creditCard)
            .build();
    }
}
