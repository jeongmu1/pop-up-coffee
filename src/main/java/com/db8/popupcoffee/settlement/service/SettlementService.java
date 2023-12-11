package com.db8.popupcoffee.settlement.service;

import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import com.db8.popupcoffee.settlement.domain.ProductOrder;
import com.db8.popupcoffee.settlement.domain.ProductOrderStatus;
import com.db8.popupcoffee.settlement.domain.Settlement;
import com.db8.popupcoffee.settlement.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SettlementService {

    private final SettlementRepository settlementRepository;

    @Transactional
    public void doSettlement(SpaceRentalAgreement rental) {
        long revenueSettle = (long) (rental.getProductOrders().stream()
            .filter(order -> order.getStatus().equals(ProductOrderStatus.COMPLETED))
            .mapToLong(ProductOrder::getTotalPayment).sum() * (100
            - rental.getRevenueSharePercentage()) / 100);
        Settlement settlement = settlementRepository.save(
            Settlement.builder().spaceRentalAgreement(rental).settledRevenue(revenueSettle)
                .build());
        log.info("Settlement : {}", settlement);
    }
}
