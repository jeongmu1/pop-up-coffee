package com.db8.popupcoffee.reservation.repository;

import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.reservation.domain.FixedReservation;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import java.util.List;

public interface FlexibleReservationCustomRepository {

    List<FlexibleReservation> findByMerchantAndFixedReservation(Merchant merchant, FixedReservation fixedReservation);
    List<FlexibleReservation> findByMerchant(Merchant merchant);
}
