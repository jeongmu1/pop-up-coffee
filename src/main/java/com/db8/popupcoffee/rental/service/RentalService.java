package com.db8.popupcoffee.rental.service;

import com.db8.popupcoffee.global.util.FeeCalculator;
import com.db8.popupcoffee.rental.controller.dto.request.ChangeStatusRequest;
import com.db8.popupcoffee.rental.controller.dto.response.SimpleRentalInfo;
import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import com.db8.popupcoffee.rental.domain.SpaceRentalStatus;
import com.db8.popupcoffee.rental.repository.SpaceRentalAgreementRepository;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.FixedReservationStatus;
import com.db8.popupcoffee.settlement.service.SettlementService;
import com.db8.popupcoffee.space.domain.Space;
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
    private final SpaceRepository spaceRepository;
    private final FeeCalculator feeCalculator;
    private final SettlementService settlementService;

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

    public void createSpaceRental(FixedReservation fixedReservation, Space space) {
        var rental = spaceRentalAgreementRepository.save(SpaceRentalAgreement.of(fixedReservation,
            feeCalculator.calculateRentalFee(fixedReservation.getStartDate(),
                fixedReservation.getEndDate()), space));
        fixedReservation.setSpaceRentalAgreement(rental);
        fixedReservation.setStatus(FixedReservationStatus.FIXED);
    }

    @Transactional
    public void updateRentalStatus(Long rentalId, ChangeStatusRequest request) {
        var rental = spaceRentalAgreementRepository.findById(rentalId).orElseThrow();
        if ((request.status().equals(SpaceRentalStatus.COMPLETED)) && (rental.getSettlements()
            .isEmpty())) {
            settlementService.doSettlement(rental);
        }
        rental.setRentalStatus(request.status());
    }

    @Transactional
    public void updateToNextStatus(Long rentalId) {
        var rental = spaceRentalAgreementRepository.findById(rentalId).orElseThrow();
        SpaceRentalStatus nextStatus = rental.getRentalStatus().next();
        if (nextStatus != null) {
            rental.setRentalStatus(nextStatus);
            if (nextStatus.equals(SpaceRentalStatus.COMPLETED)) {
                settlementService.doSettlement(rental);
            }
        }
    }
}
