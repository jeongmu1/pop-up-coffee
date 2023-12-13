package com.db8.popupcoffee.member.service.dto.request;

import com.db8.popupcoffee.member.domain.Member;
import com.db8.popupcoffee.member.domain.PointHistory;

public record SurveyPointRequest(
        String reason
) {
    public static PointHistory createSurveyRewardHistory(Member member, int newChanges) {
        return PointHistory.builder()
                .reason("설문조사 +500")
                .changes(newChanges)
                .member(member)
                .build();
    }
}
