package com.db8.popupcoffee.contract.repository;

import com.db8.popupcoffee.contract.domain.MerchantContract;
import com.db8.popupcoffee.merchant.domain.Merchant;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface MerchantContractRepository extends CrudRepository<MerchantContract, Long> {

    Optional<MerchantContract> findByMerchantAndExpireAtAfter(Merchant merchant, LocalDate yesterday);
}
