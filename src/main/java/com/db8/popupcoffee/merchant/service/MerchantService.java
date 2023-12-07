package com.db8.popupcoffee.merchant.service;

import com.db8.popupcoffee.merchant.controller.dto.request.CreateMerchantRequest;
import com.db8.popupcoffee.merchant.domain.BusinessType;
import com.db8.popupcoffee.merchant.repository.BusinessTypeRepository;
import com.db8.popupcoffee.merchant.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private MerchantRepository merchantRepository;
    private BusinessTypeRepository businessTypeRepository;

    @Transactional
    public void createMerchant(CreateMerchantRequest form) {
        BusinessType businessType = businessTypeRepository.findById(form.businessTypeId())
            .orElseThrow();
        merchantRepository.save(form.toEntity(businessType));
    }
}
