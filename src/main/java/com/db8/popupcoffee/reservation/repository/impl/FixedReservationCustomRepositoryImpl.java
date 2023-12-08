package com.db8.popupcoffee.reservation.repository.impl;

import com.db8.popupcoffee.contract.domain.QMerchantContract;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.domain.QMerchant;
import com.db8.popupcoffee.rental.domain.QSpaceRentalAgreement;
import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.QFixedReservation;
import com.db8.popupcoffee.reservation.repository.FixedReservationCustomRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FixedReservationCustomRepositoryImpl implements FixedReservationCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FixedReservation> findByMerchantAndSpaceRentalAgreement(
        Merchant merchant,
        SpaceRentalAgreement spaceRentalAgreement
    ) {
        QFixedReservation qFixedReservation = QFixedReservation.fixedReservation;
        QSpaceRentalAgreement qSpaceRentalAgreement = QSpaceRentalAgreement.spaceRentalAgreement;
        QMerchantContract qMerchantContract = QMerchantContract.merchantContract;
        QMerchant qMerchant = QMerchant.merchant;

        return jpaQueryFactory.selectFrom(qFixedReservation)
            .join(qFixedReservation.spaceRentalAgreement, qSpaceRentalAgreement)
            .where(eqOrIsNull(qSpaceRentalAgreement, spaceRentalAgreement))
            .join(qFixedReservation.merchantContract, qMerchantContract)
            .join(qMerchantContract.merchant, qMerchant)
            .where(qMerchant.eq(merchant))
            .fetch();
    }

    private Predicate eqOrIsNull(QSpaceRentalAgreement qSpaceRentalAgreement,
        SpaceRentalAgreement spaceRentalAgreement) {
        if (spaceRentalAgreement == null) {
            return qSpaceRentalAgreement.isNull();
        }

        return qSpaceRentalAgreement.eq(spaceRentalAgreement);
    }
}
