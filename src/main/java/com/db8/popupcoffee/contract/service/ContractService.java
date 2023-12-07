package com.db8.popupcoffee.contract.service;

import com.db8.popupcoffee.contract.controller.dto.request.CreateContractRequest;
import com.db8.popupcoffee.contract.repository.MerchantContractRepository;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.merchant.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
