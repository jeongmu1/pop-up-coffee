package com.db8.popupcoffee.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Policy extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PolicyCategory category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value; // 여러 타입 지정을 위해 String 으로 지정
}
