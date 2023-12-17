package com.db8.popupcoffee.settlement.repository;

import com.db8.popupcoffee.settlement.domain.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

}
