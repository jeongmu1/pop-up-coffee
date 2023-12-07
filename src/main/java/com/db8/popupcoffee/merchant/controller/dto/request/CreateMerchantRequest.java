package com.db8.popupcoffee.merchant.controller.dto.request;

import com.db8.popupcoffee.merchant.domain.BusinessType;
import com.db8.popupcoffee.merchant.domain.Merchant;

public record CreateMerchantRequest(
    String name,
    String address,
    Long businessTypeId,
    String contactPhone,
    String username,
    String password
) {

    public Merchant toEntity(BusinessType businessType) {
        return Merchant.builder()
            .name(name)
            .address(address)
            .phone(contactPhone)
            .businessType(businessType)
            .username(username)
            .password(password)
            .build();
    }
}
