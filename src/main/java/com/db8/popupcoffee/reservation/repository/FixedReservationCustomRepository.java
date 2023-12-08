package com.db8.popupcoffee.reservation.repository;

import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import java.util.List;

public interface FixedReservationCustomRepository {

    List<FixedReservation> findByMerchantAndSpaceRentalAgreement(Merchant merchant, SpaceRentalAgreement spaceRentalAgreement);
    List<FixedReservation> findByMerchant(Merchant merchant);
}
