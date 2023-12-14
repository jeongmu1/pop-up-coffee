package com.db8.popupcoffee.merchant.repository.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantRanking {
    private Long ranking;
    private String merchantName;
    private String businessType;
    private Long totalRevenue;
}
