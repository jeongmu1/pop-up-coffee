package com.db8.popupcoffee.reservation.service;

import com.db8.popupcoffee.contract.domain.MerchantContract;
import com.db8.popupcoffee.contract.service.ContractService;
import com.db8.popupcoffee.global.util.FeeCalculator;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.repository.MerchantRepository;
import com.db8.popupcoffee.reservation.repository.FixedReservationRepository;
import com.db8.popupcoffee.reservation.service.dto.CreateFixedReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final FixedReservationRepository fixedReservationRepository;
    private final ContractService contractService;
    private final MerchantRepository merchantRepository;
    private final FeeCalculator feeCalculator;

    public void progressFixedReservation(CreateFixedReservationDto dto) {
        Merchant merchant = merchantRepository.findById(dto.merchantId()).orElseThrow();
        MerchantContract contract = contractService.findActivatedContract(merchant);
        fixedReservationRepository.save(dto.toEntity(contract,
            feeCalculator.calculateRentalFee(dto.startDate(), dto.endDate())));
    }
}
