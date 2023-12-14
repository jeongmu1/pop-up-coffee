package com.db8.popupcoffee.merchant.repository;

import com.db8.popupcoffee.merchant.repository.dto.MerchantRanking;
import java.util.List;

public interface MerchantCustomRepository {

    List<MerchantRanking> findMerchantsOrderByRevenue();
}
