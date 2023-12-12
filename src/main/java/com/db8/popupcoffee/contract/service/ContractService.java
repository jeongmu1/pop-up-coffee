package com.db8.popupcoffee.contract.service;

import com.db8.popupcoffee.contract.controller.dto.request.CreateContractRequest;
import com.db8.popupcoffee.contract.domain.MerchantContract;
import com.db8.popupcoffee.contract.repository.MerchantContractRepository;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final MerchantContractRepository merchantContractRepository;
    private final MerchantRepository merchantRepository;

    public void processContract(CreateContractRequest form) {
        Merchant merchant = merchantRepository.findById(form.merchantId())
            .orElseThrow();
        merchantContractRepository.save(form.toEntity(merchant));
    }

    public MerchantContract findActivatedContract(Merchant merchant) {
        return merchantContractRepository.findByMerchantAndExpireAtAfter(merchant,
            LocalDate.now().minusDays(1L)).orElseThrow();
    }

    public List<Merchant> findMerchantsWithoutContracts() {
        Iterable<Merchant> allMerchantsIterable = merchantRepository.findAll();
        List<Merchant> allMerchants = StreamSupport.stream(allMerchantsIterable.spliterator(), false)
            .collect(Collectors.toList());
        List<Merchant> merchantsWithoutContracts = new ArrayList<>();

        for (Merchant merchant : allMerchants) {
            if (!merchantContractRepository.existsByMerchant(merchant)) {
                merchantsWithoutContracts.add(merchant);
            }
        }

        return merchantsWithoutContracts;
    }
}
