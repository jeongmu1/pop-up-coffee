package com.db8.popupcoffee.merchant.controller.dto.request;

import com.db8.popupcoffee.global.domain.AuthenticationInfo;

public record MerchantLoginForm(
    String username,
    String password
) {

    public AuthenticationInfo toAuthenticationInfo() {
        return new AuthenticationInfo(username, password);
    }
}
