package com.db8.popupcoffee.reservation.service;

import com.db8.popupcoffee.contract.domain.MerchantContract;
import com.db8.popupcoffee.contract.service.ContractService;
import com.db8.popupcoffee.global.util.FeeCalculator;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.repository.MerchantRepository;
import com.db8.popupcoffee.reservation.domain.FlexibleReservationRepository;
import com.db8.popupcoffee.reservation.repository.FixedReservationRepository;
import com.db8.popupcoffee.reservation.service.dto.CreateFixedReservationDto;
import com.db8.popupcoffee.reservation.service.dto.CreateFlexibleReservationDto;
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

    @Transactional
    public void progressFixedReservation(CreateFixedReservationDto dto) {
        MerchantContract contract = findActivatedMerchantContract(dto.merchantId());
        fixedReservationRepository.save(dto.toEntity(contract,
            feeCalculator.calculateRentalFee(dto.startDate(), dto.endDate())));
    }

    @Transactional
    public void progressFlexibleReservation(CreateFlexibleReservationDto dto) {
        MerchantContract contract = findActivatedMerchantContract(dto.merchantId());
        flexibleReservationRepository.save(dto.toEntity(contract));
    }

    private MerchantContract findActivatedMerchantContract(long merchantId) {
        return merchantRepository.findById(merchantId)
            .map(contractService::findActivatedContract)
            .orElseThrow();
    }
}
