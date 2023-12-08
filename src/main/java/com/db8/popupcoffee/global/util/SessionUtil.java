package com.db8.popupcoffee.global.util;

import com.db8.popupcoffee.merchant.controller.dto.MerchantSessionInfo;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtil {

    public static final String MERCHANT_SESSION_KEY = "merchant";

    public static MerchantSessionInfo getMerchantSessionInfo(HttpSession session) {
        return (MerchantSessionInfo) session.getAttribute(MERCHANT_SESSION_KEY);
    }
}
