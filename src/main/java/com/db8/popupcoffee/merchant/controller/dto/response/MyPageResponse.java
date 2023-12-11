package com.db8.popupcoffee.merchant.controller.dto.response;

import com.db8.popupcoffee.merchant.domain.Merchant;

public record MyPageResponse(
        Merchant merchant,
        int scoreForNextGrade,
        int currentGradeMinScore,
        int nextGradeMinScore
) {


}
