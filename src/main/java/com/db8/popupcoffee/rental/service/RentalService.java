package com.db8.popupcoffee.rental.service;

import com.db8.popupcoffee.global.util.FeeCalculator;
import com.db8.popupcoffee.global.util.Policy;
import com.db8.popupcoffee.merchant.domain.Grade;
import com.db8.popupcoffee.merchant.domain.GradeScoreHistory;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.repository.GradeScoreHistoryRepository;
import com.db8.popupcoffee.rental.controller.dto.request.ChangeStatusRequest;
import com.db8.popupcoffee.rental.controller.dto.response.SimpleRentalInfo;
import com.db8.popupcoffee.rental.domain.Duration;
import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import com.db8.popupcoffee.rental.domain.SpaceRentalStatus;
import com.db8.popupcoffee.rental.repository.SpaceRentalAgreementRepository;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.FixedReservationStatus;
import com.db8.popupcoffee.settlement.domain.ProductOrder;
import com.db8.popupcoffee.settlement.service.SettlementService;
import com.db8.popupcoffee.space.domain.Space;
import com.db8.popupcoffee.space.repository.SpaceRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    private static final String SCORE_CHANGE_SETTLEMENT_REASON = "대여 종료";
    private static final String SCORE_CHANGE_FIRST_RENTAL = "첫 대여 보너스";
    private final GradeScoreHistoryRepository gradeScoreHistoryRepository;

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
        var merchant = fixedReservation.getMerchantContract().getMerchant();
        long fee = feeCalculator.calculateRentalFee(fixedReservation.getStartDate(),
            fixedReservation.getEndDate(), Grade.from(merchant.getGradeScore()));
        if (fixedReservation.isFromFlexibleReservation()) {
            fee = (long) (fee * (100 - Policy.FLEXIBLE_RESERVATION_DISCOUNT_PERCENTAGE) / 100);
        }
        var rental = spaceRentalAgreementRepository.save(
            SpaceRentalAgreement.of(fixedReservation, fee, space));
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
                finishRental(rental);
            }
        }
    }

    private void finishRental(SpaceRentalAgreement rental) {
        Duration duration = rental.getRentalDuration();
        long days = ChronoUnit.DAYS.between(duration.getStartDate(),
            duration.getEndDate().plusDays(1L));
        long totalRevenue = rental.getProductOrders().stream()
            .mapToLong(ProductOrder::getTotalPayment).sum();

        Merchant merchant = rental.getMerchantContract().getMerchant();
        if (!merchant.isFirstRentalOvered()) {
            int firstRentalBonus = Policy.FIRST_RENTAL_SCORE_CHANGES;
            merchant.setFirstRentalOvered(true);
            gradeScoreHistoryRepository.save(GradeScoreHistory.builder().changes(firstRentalBonus).merchant(merchant).reason(SCORE_CHANGE_FIRST_RENTAL).build());
            merchant.setGradeScore(merchant.getGradeScore() + firstRentalBonus);
        }
        int scoreChanges = (int) (days * Policy.SCORE_CHANGES_PER_DAY
            + totalRevenue / Policy.REVENUE_FOR_ONE_SCORE);
        merchant.setGradeScore(merchant.getGradeScore() + scoreChanges);
        gradeScoreHistoryRepository.save(GradeScoreHistory.builder().changes(scoreChanges).reason(SCORE_CHANGE_SETTLEMENT_REASON).merchant(merchant).build());
    }
}
