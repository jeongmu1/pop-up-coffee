package com.db8.popupcoffee.settlement.repository;

import com.db8.popupcoffee.settlement.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {

}
