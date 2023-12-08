package com.db8.popupcoffee.rental.repository;

import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import java.time.LocalDate;
import java.util.List;

public interface SpaceRentalAgreementCustomRepository {

    int countBySpecificDate(LocalDate date);
    List<SpaceRentalAgreement> findByMerchant(Merchant merchant);
}
