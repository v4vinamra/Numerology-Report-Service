package com.magicmond.global.numerologyreportservice.repository;

import com.magicmond.global.numerologyreportservice.entities.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderInfo, Long> {
    OrderInfo findByOrderId(String orderId);

}
