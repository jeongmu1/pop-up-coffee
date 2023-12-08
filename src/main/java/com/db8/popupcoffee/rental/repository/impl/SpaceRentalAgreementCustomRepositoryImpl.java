package com.db8.popupcoffee.rental.repository.impl;

import com.db8.popupcoffee.contract.domain.QMerchantContract;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.domain.QMerchant;
import com.db8.popupcoffee.rental.domain.QSpaceRentalAgreement;
import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import com.db8.popupcoffee.rental.repository.SpaceRentalAgreementCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpaceRentalAgreementCustomRepositoryImpl implements
    SpaceRentalAgreementCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public int countBySpecificDate(LocalDate date) {
        QSpaceRentalAgreement qSpaceRentalAgreement = QSpaceRentalAgreement.spaceRentalAgreement;
        return jpaQueryFactory.selectFrom(qSpaceRentalAgreement)
            .where(qSpaceRentalAgreement.rentalDuration.startDate.loe(date)
                .and(qSpaceRentalAgreement.rentalDuration.endDate.goe(date)))
            .fetch().size();
    }

    @Override
    public List<SpaceRentalAgreement> findByMerchant(Merchant merchant) {
        QSpaceRentalAgreement qSpaceRentalAgreement = QSpaceRentalAgreement.spaceRentalAgreement;
        QMerchantContract qMerchantContract = QMerchantContract.merchantContract;
        QMerchant qMerchant = QMerchant.merchant;

        return jpaQueryFactory.select(qSpaceRentalAgreement)
            .from(qSpaceRentalAgreement)
            .join(qSpaceRentalAgreement.merchantContract, qMerchantContract)
            .join(qMerchantContract.merchant, qMerchant)
            .where(qMerchant.eq(merchant))
            .fetch();
    }
}
