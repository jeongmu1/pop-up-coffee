package com.db8.popupcoffee.member.controller.dto;

import com.db8.popupcoffee.global.domain.AuthenticationInfo;
import com.db8.popupcoffee.member.domain.Member;
import lombok.Builder;

@Builder
public record MemberSessionInfo(
        Long id,
        AuthenticationInfo authenticationInfo
) {

    public static MemberSessionInfo from(Member member) {
        return MemberSessionInfo.builder()
                .id(member.getId())
                .authenticationInfo(member.getAuthenticationInfo())
                .build();
    }
}

