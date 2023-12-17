package com.db8.popupcoffee.settlement.service;

import com.db8.popupcoffee.global.util.Policy;
import com.db8.popupcoffee.member.domain.Member;
import com.db8.popupcoffee.member.domain.PointHistory;
import com.db8.popupcoffee.member.repository.MemberRepository;
import com.db8.popupcoffee.member.repository.PointHistoryRepository;
import com.db8.popupcoffee.rental.domain.SpaceRentalAgreement;
import com.db8.popupcoffee.rental.repository.SpaceRentalAgreementRepository;
import com.db8.popupcoffee.settlement.controller.dto.request.OrderForm;
import com.db8.popupcoffee.settlement.domain.ProductOrder;
import com.db8.popupcoffee.settlement.domain.ProductOrderStatus;
import com.db8.popupcoffee.settlement.domain.Settlement;
import com.db8.popupcoffee.settlement.repository.ProductOrderRepository;
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
    private final MemberRepository memberRepository;
    private final SpaceRentalAgreementRepository spaceRentalAgreementRepository;
    private final ProductOrderRepository productOrderRepository;
    private final PointHistoryRepository pointHistoryRepository;

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

    @Transactional
    public void createProductOrder(OrderForm orderForm) {
        Member member = memberRepository.findById(orderForm.memberId()).orElseThrow();
        SpaceRentalAgreement rental = spaceRentalAgreementRepository.findById(
            orderForm.spaceRentalId()).orElseThrow();

        if (member.getPoint() - orderForm.usedPoint() < 0) {
            throw new IllegalArgumentException("포인트 부족");
        }

        int pointChanges = (int) ((orderForm.totalPayment() - orderForm.usedPoint())
            * Policy.ORDER_POINT_SAVING_RATE);
        member.setPoint(
            member.getPoint() + pointChanges);

        pointHistoryRepository.save(
            PointHistory.builder().changes(pointChanges).reason("주문에 의한 적립").member(member)
                .build());

        productOrderRepository.save(ProductOrder.builder()
            .member(member)
            .paymentType(orderForm.paymentType())
            .usedPoint(orderForm.usedPoint())
            .spaceRentalAgreement(rental)
            .status(ProductOrderStatus.COMPLETED)
            .totalPayment(orderForm.totalPayment())
            .build());
    }
}
