package com.db8.popupcoffee.reservation.repository.impl;

import com.db8.popupcoffee.contract.domain.QMerchantContract;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.domain.QMerchant;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.QFixedReservation;
import com.db8.popupcoffee.reservation.repository.FixedReservationCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FixedReservationCustomRepositoryImpl implements FixedReservationCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FixedReservation> findByMerchantAndFromFlexible(Merchant merchant,
        boolean fromFlexible) {
        QFixedReservation qFixedReservation = QFixedReservation.fixedReservation;
        QMerchantContract qMerchantContract = QMerchantContract.merchantContract;
        QMerchant qMerchant = QMerchant.merchant;

        return jpaQueryFactory.selectFrom(qFixedReservation)
            .join(qFixedReservation.merchantContract, qMerchantContract)
            .join(qMerchantContract.merchant, qMerchant)
            .where(qMerchant.eq(merchant))
            .where(qFixedReservation.fromFlexibleReservation.eq(fromFlexible))
            .fetch();
    }

    @Override
    public List<FixedReservation> findByMerchant(Merchant merchant) {
        QFixedReservation qFixedReservation = QFixedReservation.fixedReservation;
        QMerchantContract qMerchantContract = QMerchantContract.merchantContract;
        QMerchant qMerchant = QMerchant.merchant;

        return jpaQueryFactory.selectFrom(qFixedReservation)
            .join(qFixedReservation.merchantContract, qMerchantContract)
            .join(qMerchantContract.merchant, qMerchant)
            .where(qMerchant.eq(merchant))
            .fetch();
    }
}
