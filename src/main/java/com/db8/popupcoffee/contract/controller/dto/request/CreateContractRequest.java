package com.db8.popupcoffee.contract.controller.dto.request;

import com.db8.popupcoffee.contract.domain.MerchantContract;
import com.db8.popupcoffee.global.domain.Contact;
import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.settlement.domain.SettlementAccount;
import java.time.LocalDate;

public record CreateContractRequest(
    Long merchantId,
    LocalDate expireAt,
    String contactManager,
    String contactPhone,
    String accountNumber,
    String accountBank,
    String accountOwner
) {

    public MerchantContract toEntity(Merchant merchant) {
        return MerchantContract.builder()
            .merchant(merchant)
            .expireAt(expireAt)
            .contact(
                Contact.builder().contactManager(contactManager).contactPhone(contactPhone).build())
            .settlementAccount(
                SettlementAccount.builder().accountBank(accountBank).accountOwner(accountOwner)
                    .accountNumber(accountNumber).build())
            .build();
    }
}
