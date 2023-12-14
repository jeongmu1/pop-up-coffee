package com.db8.popupcoffee.reservation.service;

import com.db8.popupcoffee.contract.domain.MerchantContract;
import com.db8.popupcoffee.contract.service.ContractService;
import com.db8.popupcoffee.global.domain.CreditCard;
import com.db8.popupcoffee.global.util.FeeCalculator;
import com.db8.popupcoffee.global.util.Policy;
import com.db8.popupcoffee.merchant.domain.BusinessType;
import com.db8.popupcoffee.merchant.domain.Grade;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.repository.BusinessTypeRepository;
import com.db8.popupcoffee.merchant.repository.MerchantRepository;
import com.db8.popupcoffee.rental.domain.SpaceRentalStatus;
import com.db8.popupcoffee.rental.service.RentalService;
import com.db8.popupcoffee.reservation.controller.dto.request.FlexibleChargeRequest;
import com.db8.popupcoffee.reservation.controller.dto.response.FeeInfo;
import com.db8.popupcoffee.reservation.controller.dto.response.FlexibleReservationInfo;
import com.db8.popupcoffee.reservation.controller.dto.response.ReservationHistory;
import com.db8.popupcoffee.reservation.domain.DesiredDate;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.FixedReservationStatus;
import com.db8.popupcoffee.reservation.domain.FlexibleReservationStatus;
import com.db8.popupcoffee.reservation.repository.DesiredDateRepository;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import com.db8.popupcoffee.reservation.repository.FlexibleReservationRepository;
import com.db8.popupcoffee.reservation.repository.FixedReservationRepository;
import com.db8.popupcoffee.reservation.service.dto.CreateFixedReservationDto;
import com.db8.popupcoffee.reservation.service.dto.CreateFlexibleReservationDto;
import com.db8.popupcoffee.reservation.service.dto.FixedDatesInfoDto;
import com.db8.popupcoffee.space.controller.dto.request.ReservationIdDto;
import com.db8.popupcoffee.space.domain.Space;
import com.db8.popupcoffee.space.service.SpaceService;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final FixedReservationRepository fixedReservationRepository;
    private final ContractService contractService;
    private final MerchantRepository merchantRepository;
    private final FeeCalculator feeCalculator;
    private final FlexibleReservationRepository flexibleReservationRepository;
    private final DesiredDateRepository desiredDateRepository;
    private final BusinessTypeRepository businessTypeRepository;
    private final RentalService rentalService;
    private final SpaceService spaceService;

    @Transactional
    public void progressFixedReservation(CreateFixedReservationDto dto) {
        MerchantContract contract = findActivatedMerchantContract(dto.merchantId());
        BusinessType businessType = businessTypeRepository.findById(dto.businessTypeId())
            .orElseThrow();
        List<Space> availableSpaces = spaceService.findAvailableSpaces(dto.startDate(),
            dto.endDate());
        if (availableSpaces.isEmpty()) {
            throw new IllegalArgumentException("해당 날짜에 예약 가능한 공간이 없습니다.");
        }
        var fixed = fixedReservationRepository.save(dto.toEntity(contract, businessType,
            feeCalculator.calculateRentalFee(dto.startDate(), dto.endDate(),
                Grade.from(contract.getMerchant().getGradeScore())),
            feeCalculator.calculateRentalDeposit(dto.startDate(), dto.endDate())));
        rentalService.createSpaceRental(fixed, availableSpaces.stream().findFirst().orElseThrow());
    }

    @Transactional
    public void progressFlexibleReservation(CreateFlexibleReservationDto dto) {
        MerchantContract contract = findActivatedMerchantContract(dto.merchantId());
        BusinessType businessType = businessTypeRepository.findById(dto.businessTypeId())
            .orElseThrow();
        FlexibleReservation reservation = flexibleReservationRepository.save(
            dto.toEntity(contract, businessType));
        desiredDateRepository.saveAll(
            dto.desiredDates().stream().filter(Objects::nonNull)
                .map(date -> new DesiredDate(date, reservation)).toList());
    }

    @Transactional(readOnly = true)
    public FeeInfo getFeeInfo(FixedDatesInfoDto dto) {
        Merchant merchant = merchantRepository.findById(dto.merchantId()).orElseThrow();
        int gradeScore = merchant.getGradeScore();
        long fee = feeCalculator.calculateRentalFee(dto.start(), dto.end(), Grade.from(gradeScore));
        return new FeeInfo(fee, Grade.from(gradeScore).getDaysForNextGrade(gradeScore));
    }

    @Transactional(readOnly = true)
    public List<FlexibleReservationInfo> findNonFixedFlexibleRepositories() {
        return flexibleReservationRepository.findByStatusIn(
                List.of(FlexibleReservationStatus.WAITING,
                    FlexibleReservationStatus.SPACE_TEMPORARY_FIXED)).stream()
            .map(FlexibleReservationInfo::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationHistory> getReservationHistories(long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow();
        Stream<ReservationHistory> onlyFixeds =
            fixedReservationRepository.findByMerchantAndFromFlexible(merchant, false).stream()
                .map(ReservationHistory::of);
        Stream<ReservationHistory> fromFlexibles =
            flexibleReservationRepository.findByMerchant(merchant).stream()
                .map(flexible -> ReservationHistory.of(flexible, feeCalculator));

        return Stream.concat(onlyFixeds, fromFlexibles)
            .sorted(Comparator.comparing(ReservationHistory::reservedDate).reversed()).toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationHistory> getFixedHistories(long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow();
        Stream<ReservationHistory> onlyFixeds =
            fixedReservationRepository.findByMerchantAndFromFlexible(merchant, false).stream()
                .map(ReservationHistory::of);

        return onlyFixeds.toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationHistory> findNotRentedFixedReservations() {
        return fixedReservationRepository.findByStatusIsNot(FixedReservationStatus.FIXED).stream()
            .map(ReservationHistory::of).toList();
    }

    @Transactional
    public void updateStatusToSpaceFixed(ReservationIdDto dto) {
        if (!dto.fromFlexible()) {
            log.info("고정 예약의 상태 변경 요청");
            throw new IllegalArgumentException("고정 예약의 상태는 이미 확정 또는 취소입니다.");
        }

        var flexible = flexibleReservationRepository.findById(dto.id()).orElseThrow();
        flexible.setStatus(FlexibleReservationStatus.SPACE_FIXED);
    }

    @Transactional
    public void processPayment(FlexibleChargeRequest request) {
        var flexible = flexibleReservationRepository.findById(request.reservationId())
            .orElseThrow();
        if (!flexible.getStatus().equals(FlexibleReservationStatus.SPACE_FIXED)) {
            throw new IllegalArgumentException(
                "해당 예약 상태에서는 결제를 진행할 수 없습니다 : " + flexible.getStatus().name());
        }
        long rentalFee =
            (long) (feeCalculator.calculateRentalFee(flexible.getTemporalRentalStartDate(),
                flexible.getTemporalRentalEndDate(),
                Grade.from(flexible.getMerchantContract().getMerchant().getGradeScore())) * (100
                - Policy.FLEXIBLE_RESERVATION_DISCOUNT_PERCENTAGE) / 100);
        long rentalDeposit = feeCalculator.calculateRentalDeposit(
            flexible.getTemporalRentalStartDate(), flexible.getTemporalRentalEndDate());
        var fixed = fixedReservationRepository.save(
            FixedReservation.of(
                flexible,
                CreditCard.builder().
                    creditCardNumber(request.ccNumber())
                    .creditCardExpireAt(request.ccExpiration())
                    .creditCardCvc(request.ccCvc())
                    .build(),
                rentalFee,
                rentalDeposit));
        flexible.setFixedReservation(fixed);
        flexible.setStatus(FlexibleReservationStatus.RESERVATION_FIXED);
        rentalService.createSpaceRental(fixed, flexible.getTemporalSpace());
    }

    @Transactional
    public void cancelReservation(ReservationIdDto dto) {
        if (dto.fromFlexible()) {
            var flexible = flexibleReservationRepository.findById(dto.id()).orElseThrow();
            flexible.setTemporalRentalStartDate(null);
            flexible.setTemporalRentalStartDate(null);
            flexible.setTemporalSpace(null);
            flexible.setStatus(FlexibleReservationStatus.CANCELED);

            if (flexible.getFixedReservation() != null) {
                cancelFixedReservation(flexible.getFixedReservation());
            }
        } else {
            cancelFixedReservation(fixedReservationRepository.findById(dto.id()).orElseThrow());
        }
    }

    private void cancelFixedReservation(FixedReservation fixedReservation) {
        if (fixedReservation.getStatus().equals(FixedReservationStatus.FIXED)) {
            var rental = fixedReservation.getSpaceRentalAgreement();
            rental.setRentalStatus(SpaceRentalStatus.CANCELED);
            rental.setSpace(null);
        }
        fixedReservation.setStatus(FixedReservationStatus.CANCELED);
    }

    private MerchantContract findActivatedMerchantContract(long merchantId) {
        return merchantRepository.findById(merchantId)
            .map(contractService::findActivatedContract)
            .orElseThrow();
    }

    public List<ReservationHistory> getFlexibleHistories(Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow();
        List<FlexibleReservation> reservations = flexibleReservationRepository.findByMerchant(
            merchant);
        return reservations.stream().map(flexible -> ReservationHistory.of(flexible, feeCalculator))
            .toList();
    }
}
