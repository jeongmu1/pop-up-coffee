package com.db8.popupcoffee.reservation.repository;

import com.db8.popupcoffee.merchant.domain.Merchant;
import com.db8.popupcoffee.reservation.domain.FlexibleReservation;
import java.util.List;

public interface FlexibleReservationCustomRepository {

    List<FlexibleReservation> findByMerchant(Merchant merchant);
}
