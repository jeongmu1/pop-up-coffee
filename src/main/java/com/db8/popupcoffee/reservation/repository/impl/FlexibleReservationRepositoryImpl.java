package com.db8.popupcoffee.reservation.repository.impl;

import com.db8.popupcoffee.contract.domain.QMerchantContract;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.domain.QMerchant;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import com.db8.popupcoffee.reservation.domain.QFlexibleReservation;
import com.db8.popupcoffee.reservation.repository.FlexibleReservationCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlexibleReservationRepositoryImpl implements FlexibleReservationCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FlexibleReservation> findByMerchant(Merchant merchant) {
        QFlexibleReservation qFlexibleReservation = QFlexibleReservation.flexibleReservation;
        QMerchantContract qMerchantContract = QMerchantContract.merchantContract;
        QMerchant qMerchant = QMerchant.merchant;

        return jpaQueryFactory.selectFrom(qFlexibleReservation)
            .join(qFlexibleReservation.merchantContract, qMerchantContract)
            .join(qMerchantContract.merchant, qMerchant)
            .where(qMerchant.eq(merchant))
            .fetch();
    }
}
