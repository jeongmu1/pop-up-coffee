package com.db8.popupcoffee.merchant.domain;

import static com.db8.popupcoffee.global.util.Policy.*;

import lombok.Getter;

@Getter
public enum Grade {
    WHITE(0, WHITE_SHARE_PERCENTAGE),
    GREEN(GREEN_MIN_SCORE, GREEN_SHARE_PERCENTAGE),
    PURPLE(PURPLE_MIN_SCORE, PURPLE_SHARE_PERCENTAGE),
    VIP(VIP_MIN_SCORE, VIP_SHARE_PERCENTAGE);

    @Getter
    private final int minimumScore;
    private final double sharingPercentage;

    Grade(int minimumScore, double sharingPercentage) {
        this.minimumScore = minimumScore;
        this.sharingPercentage = sharingPercentage;
    }

    public static Grade from(int gradeScore) {
        if (gradeScore > VIP.minimumScore) {
            return VIP;
        }
        if (gradeScore > PURPLE.minimumScore) {
            return PURPLE;
        }
        if (gradeScore > GREEN.minimumScore) {
            return GREEN;
        }
        return WHITE;
    }

    public Grade nextGrade() {
        return switch (this) {
            case VIP -> VIP;
            case PURPLE -> VIP;
            case GREEN -> PURPLE;
            case WHITE -> GREEN;
        };
    }

    public int scoreForNextGrade(int gradeScore) {
        return switch (nextGrade()) {
            case VIP -> VIP_MIN_SCORE- gradeScore;
            case PURPLE -> PURPLE.minimumScore - gradeScore;
            case GREEN -> GREEN.minimumScore - gradeScore;
            case WHITE -> throw new IllegalArgumentException("다음 등급이 WHITE 가 될 수 없습니다.");
        };
    }

    public long getDaysForNextGrade(int gradeScore) {
        if (this == VIP) {
            return 0;
        }
        int rest = scoreForNextGrade(gradeScore) % SCORE_CHANGES_PER_DAY;

        if (rest > 0) {
            return scoreForNextGrade(gradeScore) / SCORE_CHANGES_PER_DAY + 1L;
        }
        return scoreForNextGrade(gradeScore) / SCORE_CHANGES_PER_DAY;
    }

    public long revenueForNextGrade(int gradeScore) {
        if (this == VIP) {
            return 0;
        }
        return (long) scoreForNextGrade(gradeScore) * REVENUE_FOR_ONE_SCORE;
    }

    public int getNextGradeMinScore() {
        if (this.nextGrade() != null) {
            return this.nextGrade().getMinimumScore();
        } else {
            throw new IllegalArgumentException("다음 등급이 없습니다.");
        }
    }
}
