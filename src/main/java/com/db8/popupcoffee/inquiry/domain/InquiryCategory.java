package com.db8.popupcoffee.inquiry.domain;

import com.db8.popupcoffee.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryCategory extends BaseEntity {

    @Column(nullable = false)
    private String category;
}
