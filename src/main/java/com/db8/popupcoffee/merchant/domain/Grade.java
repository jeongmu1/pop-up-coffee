package com.db8.popupcoffee.merchant.domain;

import static com.db8.popupcoffee.global.util.Policy.*;

public enum Grade {
    WHITE(0),
    GREEN(GREEN_MIN_SCORE),
    PURPLE(PURPLE_MIN_SCORE),
    VIP(VIP_MIN_SCORE);

    private final int minimumScore;

    Grade(int minimumScore) {
        this.minimumScore = minimumScore;
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
            case VIP -> null;
            case PURPLE -> VIP;
            case GREEN -> PURPLE;
            case WHITE -> GREEN;
        };
    }

    public int scoreForNextGrade(int gradeScore) {
        return switch (nextGrade()) {
            case VIP -> 0;
            case PURPLE -> VIP.minimumScore - gradeScore;
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
}
