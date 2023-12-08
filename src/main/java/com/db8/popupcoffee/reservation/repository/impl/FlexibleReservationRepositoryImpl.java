package com.db8.popupcoffee.reservation.repository.impl;

import com.db8.popupcoffee.contract.domain.QMerchantContract;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.domain.QMerchant;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import com.db8.popupcoffee.reservation.domain.QFixedReservation;
import com.db8.popupcoffee.reservation.domain.QFlexibleReservation;
import com.db8.popupcoffee.reservation.repository.FlexibleReservationCustomRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlexibleReservationRepositoryImpl implements FlexibleReservationCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FlexibleReservation> findByMerchantAndFixedReservation(Merchant merchant,
        FixedReservation fixedReservation) {
        QFlexibleReservation qFlexibleReservation = QFlexibleReservation.flexibleReservation;
        QMerchantContract qMerchantContract = QMerchantContract.merchantContract;
        QFixedReservation qFixedReservation = QFixedReservation.fixedReservation;
        QMerchant qMerchant = QMerchant.merchant;

        return jpaQueryFactory.selectFrom(qFlexibleReservation)
            .join(qFlexibleReservation.fixedReservation, qFixedReservation)
            .where(eqOrIsNull(qFixedReservation, fixedReservation))
            .join(qFlexibleReservation.merchantContract, qMerchantContract)
            .join(qMerchantContract.merchant, qMerchant)
            .where(qMerchant.eq(merchant))
            .fetch();
    }

    private Predicate eqOrIsNull(QFixedReservation qFixedReservation, FixedReservation fixedReservation) {
        if (fixedReservation == null) {
            return qFixedReservation.isNull();
        }

        return qFixedReservation.eq(fixedReservation);
    }
}
