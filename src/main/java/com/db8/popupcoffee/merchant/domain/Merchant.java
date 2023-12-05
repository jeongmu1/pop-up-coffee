package com.db8.popupcoffee.merchant.domain;

import com.db8.popupcoffee.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

// 업체
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Merchant extends BaseTimeEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BusinessType businessType;

    @Column(nullable = false)
    private int gradeScore = 0;

    @Column(nullable = false)
    private int warningCount = 0;

    @Column(nullable = false)
    private boolean blacklist;

    private LocalDateTime lastGradeScoreChanged;

    @Column(nullable = false)
    private int rentCount = 0;

    @Column(nullable = false)
    private String username;

    private String password;
}
