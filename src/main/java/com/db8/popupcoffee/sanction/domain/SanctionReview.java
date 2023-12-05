package com.db8.popupcoffee.sanction.domain;

import com.db8.popupcoffee.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

// 경고 / 블랙리스트 해제 신청
@Entity
@Data
@NoArgsConstructor
public class SanctionReview extends BaseTimeEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SanctionReviewType reviewType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BlackListHistory blackListHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private WarningHistory warningHistory;

    @Column(nullable = false)
    private String content;
}
