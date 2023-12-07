package com.db8.popupcoffee.merchant.domain;

import com.db8.popupcoffee.global.domain.AuthenticationInfo;
import com.db8.popupcoffee.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 업체
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Merchant extends BaseTimeEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BusinessType businessType;

    @Column(nullable = false)
    private int gradeScore = 0;

    @Column(nullable = false)
    private int warningCount = 0;

    @Column(nullable = false)
    private boolean blacklist = false;

    private LocalDateTime lastGradeScoreChanged;

    @Column(nullable = false)
    private int rentCount = 0;

    @Embedded
    private AuthenticationInfo authenticationInfo;

    @Builder
    public Merchant(
        String name,
        String address,
        String phone,
        BusinessType businessType,
        String username,
        String password) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.businessType = businessType;
        this.authenticationInfo = new AuthenticationInfo(username, password);
    }
}
