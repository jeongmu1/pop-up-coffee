package com.db8.popupcoffee.merchant.repository.impl;

import com.db8.popupcoffee.contract.domain.QMerchantContract;
import com.db8.popupcoffee.merchant.domain.QMerchant;
import com.db8.popupcoffee.merchant.repository.MerchantCustomRepository;
import com.db8.popupcoffee.merchant.repository.dto.MerchantRanking;
import com.db8.popupcoffee.rental.domain.QSpaceRentalAgreement;
import com.db8.popupcoffee.settlement.domain.QProductOrder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MerchantCustomRepositoryImpl implements MerchantCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<MerchantRanking> findMerchantsOrderByRevenue() {
        QMerchant merchant = QMerchant.merchant;
        QMerchantContract merchantContract = QMerchantContract.merchantContract;
        QSpaceRentalAgreement spaceRentalAgreement = QSpaceRentalAgreement.spaceRentalAgreement;
        QProductOrder productOrder = QProductOrder.productOrder;

        List<MerchantRanking> rankings = jpaQueryFactory
            .select(Projections.fields(MerchantRanking.class,
                merchant.name.as("merchantName"),
                merchant.businessType.name.as("businessType"),
                productOrder.totalPayment.sum().as("totalRevenue")
            ))
            .from(merchant)
            .join(merchant.merchantContracts, merchantContract)
            .join(merchantContract.spaceRentalAgreements, spaceRentalAgreement)
            .join(spaceRentalAgreement.productOrders, productOrder)
            .groupBy(merchant.name, merchant.businessType.name)
            .orderBy(productOrder.totalPayment.sum().desc())
            .fetch();

        long rank = 1;
        for (int i = 0; i < rankings.size(); i++) {
            if (i > 0 && !rankings.get(i).getTotalRevenue()
                .equals(rankings.get(i - 1).getTotalRevenue())) {
                rank = i + 1L;
            }
            MerchantRanking currentRanking = rankings.get(i);
            rankings.set(i, new MerchantRanking(rank, currentRanking.getMerchantName(),
                currentRanking.getBusinessType(), currentRanking.getTotalRevenue()));
        }

        return rankings;
    }
}
