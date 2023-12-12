package com.db8.popupcoffee.merchant.repository;

import com.db8.popupcoffee.global.domain.AuthenticationInfo;
import com.db8.popupcoffee.merchant.domain.Merchant;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MerchantRepository extends CrudRepository<Merchant, Long> {

    Optional<Merchant> findMerchantByAuthenticationInfo(AuthenticationInfo authenticationInfo);
}
