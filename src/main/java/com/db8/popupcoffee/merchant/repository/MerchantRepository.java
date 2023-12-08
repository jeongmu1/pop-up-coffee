package com.db8.popupcoffee.merchant.repository;

import com.db8.popupcoffee.global.domain.AuthenticationInfo;
import com.db8.popupcoffee.merchant.domain.Merchant;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface MerchantRepository extends CrudRepository<Merchant, Long> {

    Optional<Merchant> findMerchantByAuthenticationInfo(AuthenticationInfo authenticationInfo);
}
