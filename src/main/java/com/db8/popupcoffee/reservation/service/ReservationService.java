package com.db8.popupcoffee.reservation.service;

import com.db8.popupcoffee.contract.domain.MerchantContract;
import com.db8.popupcoffee.contract.service.ContractService;
import com.db8.popupcoffee.global.util.FeeCalculator;
import com.db8.popupcoffee.merchant.domain.BusinessType;
import com.db8.popupcoffee.merchant.domain.Grade;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.repository.BusinessTypeRepository;
import com.db8.popupcoffee.merchant.repository.MerchantRepository;
import com.db8.popupcoffee.reservation.controller.dto.response.FeeInfo;
import com.db8.popupcoffee.reservation.controller.dto.response.FlexibleReservationInfo;
import com.db8.popupcoffee.reservation.controller.dto.response.ReservationHistory;
import com.db8.popupcoffee.reservation.domain.DesiredDate;
import com.db8.popupcoffee.reservation.domain.FlexibleReservationStatus;
import com.db8.popupcoffee.reservation.repository.DesiredDateRepository;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import com.db8.popupcoffee.reservation.repository.FlexibleReservationRepository;
import com.db8.popupcoffee.reservation.repository.FixedReservationRepository;
import com.db8.popupcoffee.reservation.service.dto.CreateFixedReservationDto;
import com.db8.popupcoffee.reservation.service.dto.CreateFlexibleReservationDto;
import com.db8.popupcoffee.reservation.service.dto.FixedDatesInfoDto;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final FixedReservationRepository fixedReservationRepository;
    private final ContractService contractService;
    private final MerchantRepository merchantRepository;
    private final FeeCalculator feeCalculator;
    private final FlexibleReservationRepository flexibleReservationRepository;
    private final DesiredDateRepository desiredDateRepository;
    private final BusinessTypeRepository businessTypeRepository;

    @Transactional
    public void progressFixedReservation(CreateFixedReservationDto dto) {
        MerchantContract contract = findActivatedMerchantContract(dto.merchantId());
        BusinessType businessType = businessTypeRepository.findById(dto.businessTypeId())
            .orElseThrow();
        fixedReservationRepository.save(dto.toEntity(contract, businessType,
            feeCalculator.calculateRentalFee(dto.startDate(), dto.endDate())));
    }

    @Transactional
    public void progressFlexibleReservation(CreateFlexibleReservationDto dto) {
        MerchantContract contract = findActivatedMerchantContract(dto.merchantId());
        FlexibleReservation reservation = flexibleReservationRepository.save(
            dto.toEntity(contract));
        desiredDateRepository.saveAll(
            dto.desiredDates().stream().map(date -> new DesiredDate(date, reservation)).toList());
    }

    @Transactional(readOnly = true)
    public FeeInfo getFeeInfo(FixedDatesInfoDto dto) {
        Merchant merchant = merchantRepository.findById(dto.merchantId()).orElseThrow();
        int gradeScore = merchant.getGradeScore();
        long fee = feeCalculator.calculateRentalFee(dto.start(), dto.end());
        return new FeeInfo(fee, Grade.from(gradeScore).getDaysForNextGrade(gradeScore));
    }

    @Transactional(readOnly = true)
    public List<FlexibleReservationInfo> findNonFixedFlexibleRepositories() {
        return flexibleReservationRepository.findByStatusIn(
                List.of(FlexibleReservationStatus.WAITING,
                    FlexibleReservationStatus.SPACE_TEMPORARY_FIXED)).stream()
            .map(FlexibleReservationInfo::from).toList();
    }

    public List<ReservationHistory> getReservationHistories(long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow();
        Stream<ReservationHistory> onlyFixeds =
            fixedReservationRepository.findByMerchantAndFromFlexible(merchant, false).stream()
                .map(ReservationHistory::from);
        Stream<ReservationHistory> fromFlexibles =
            flexibleReservationRepository.findByMerchant(merchant).stream()
                .map(ReservationHistory::from);

        return Stream.concat(onlyFixeds, fromFlexibles)
            .sorted(Comparator.comparing(ReservationHistory::reservedDate).reversed()).toList();
    }

    private MerchantContract findActivatedMerchantContract(long merchantId) {
        return merchantRepository.findById(merchantId)
            .map(contractService::findActivatedContract)
            .orElseThrow();
    }
}
