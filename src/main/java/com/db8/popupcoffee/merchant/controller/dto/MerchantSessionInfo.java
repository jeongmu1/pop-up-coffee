package com.db8.popupcoffee.merchant.controller.dto;

import com.db8.popupcoffee.global.domain.AuthenticationInfo;
import com.db8.popupcoffee.merchant.domain.Merchant;
import lombok.Builder;

@Builder
public record MerchantSessionInfo(
    Long id,
    AuthenticationInfo authenticationInfo
) {

    public static MerchantSessionInfo from(Merchant merchant) {
        return MerchantSessionInfo.builder()
            .id(merchant.getId())
            .authenticationInfo(merchant.getAuthenticationInfo())
            .build();
    }
}