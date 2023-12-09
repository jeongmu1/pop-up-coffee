package com.db8.popupcoffee.member.controller.dto.request;

import com.db8.popupcoffee.global.domain.AuthenticationInfo;
import com.db8.popupcoffee.member.domain.Member;
import com.db8.popupcoffee.member.domain.MemberGrade;

public record CreateMemberRequest(
        String nickname,
        String phone,
        String email,
        String username,
        String password
) {

    public Member toEntity() {
        return Member.builder()
                .authenticationInfo(new AuthenticationInfo(username, password))
                .nickname(nickname)
                .phone(phone)
                .email(email)
                .memberGrade(MemberGrade.BRONZE)
                .build();
    }
}

