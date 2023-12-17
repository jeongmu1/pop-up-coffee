package com.db8.popupcoffee.member.controller.dto.response;

import com.db8.popupcoffee.member.domain.Member;

public record SimpleMemberInfo(
    Long memberId,
    String email
) {

    public static SimpleMemberInfo from(Member member) {
        return new SimpleMemberInfo(member.getId(), member.getNickname());
    }
}
