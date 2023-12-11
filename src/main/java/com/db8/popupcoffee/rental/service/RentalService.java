package com.db8.popupcoffee.rental.service;

import com.db8.popupcoffee.global.util.FeeCalculator;
import com.db8.popupcoffee.rental.controller.dto.request.SpaceRentalRequest;
import com.db8.popupcoffee.rental.controller.dto.response.SimpleRentalInfo;
import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import com.db8.popupcoffee.rental.repository.SpaceRentalAgreementRepository;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.repository.FixedReservationRepository;
import com.db8.popupcoffee.space.repository.SpaceRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final SpaceRentalAgreementRepository spaceRentalAgreementRepository;
    private final FixedReservationRepository fixedReservationRepository;
    private final SpaceRepository spaceRepository;
    private final FeeCalculator feeCalculator;

    @Transactional(readOnly = true)
    public List<SimpleRentalInfo> findRentalInfos() {
        return spaceRentalAgreementRepository.findAll().stream().map(SimpleRentalInfo::from)
            .sorted(Comparator.comparing(SimpleRentalInfo::startDate).reversed())
            .toList();
    }

    @Transactional(readOnly = true)
    public int countAvailableSpaces(LocalDate date) {
        return (int) spaceRepository.count()
            - spaceRentalAgreementRepository.countBySpecificDate(date);
    }

    @Transactional
    public void createSpaceRental(SpaceRentalRequest request) {
        FixedReservation fixedReservation = fixedReservationRepository.findById(
            request.fixedReservationId()).orElseThrow();
        spaceRentalAgreementRepository.save(SpaceRentalAgreement.of(fixedReservation,
            feeCalculator.calculateRentalFee(fixedReservation.getStartDate(),
                fixedReservation.getEndDate())));
    }
}
