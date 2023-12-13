package com.db8.popupcoffee.space.repository.impl;

import com.db8.popupcoffee.rental.domain.QSpaceRentalAgreement;
import com.db8.popupcoffee.reservation.domain.QFlexibleReservation;
import com.db8.popupcoffee.space.domain.QSpace;
import com.db8.popupcoffee.space.domain.Space;
import com.db8.popupcoffee.space.repository.SpaceCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpaceCustomRepositoryImpl implements SpaceCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Space> findAvailableSpaces(LocalDate startDate, LocalDate endDate) {
        QSpace qSpace = QSpace.space;
        QFlexibleReservation qFlexibleReservation = QFlexibleReservation.flexibleReservation;
        QSpaceRentalAgreement qSpaceRentalAgreement = QSpaceRentalAgreement.spaceRentalAgreement;

        // 공간을 예약한 목록을 가져오는 서브 쿼리
        List<Space> reservedSpaces = new ArrayList<>();
        reservedSpaces.addAll(jpaQueryFactory
            .select(qFlexibleReservation.temporalSpace)
            .from(qFlexibleReservation)
            .where(qFlexibleReservation.availabilityStartDate.before(endDate)
                .and(qFlexibleReservation.availabilityEndDate.after(startDate))
                .or(qFlexibleReservation.temporalRentalStartDate.before(endDate)
                    .and(qFlexibleReservation.temporalRentalEndDate.after(startDate))))
            .fetch());
        reservedSpaces.addAll(jpaQueryFactory
            .select(qSpaceRentalAgreement.space)
            .from(qSpaceRentalAgreement)
            .where(qSpaceRentalAgreement.rentalDuration.startDate.before(endDate)
                .and(qSpaceRentalAgreement.rentalDuration.endDate.after(startDate)))
            .fetch());

        // 중복 제거
        Set<Space> uniqueReservedSpaces = new HashSet<>(reservedSpaces);

        // 예약되지 않은 공간을 조회하는 메인 쿼리
        return jpaQueryFactory
            .selectFrom(qSpace)
            .where(qSpace.notIn(uniqueReservedSpaces))
            .fetch();
    }
}
