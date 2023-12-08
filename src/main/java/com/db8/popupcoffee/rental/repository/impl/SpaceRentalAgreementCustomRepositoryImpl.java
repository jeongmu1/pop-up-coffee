package com.db8.popupcoffee.rental.repository.impl;

import com.db8.popupcoffee.rental.domain.QSpaceRentalAgreement;
import com.db8.popupcoffee.rental.repository.SpaceRentalAgreementCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
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
}
