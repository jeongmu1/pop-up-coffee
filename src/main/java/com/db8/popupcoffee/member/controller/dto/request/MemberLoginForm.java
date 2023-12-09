package com.db8.popupcoffee.member.controller.dto.request;

import com.db8.popupcoffee.global.domain.AuthenticationInfo;

public record MemberLoginForm (
    String username,
    String password
) {
        public AuthenticationInfo toAuthenticationInfo() {
            return new AuthenticationInfo(username, password);
        }
    }
