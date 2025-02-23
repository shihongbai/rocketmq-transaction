package com.shbai.tx.mqtransaction.repository;

import com.shbai.tx.mqtransaction.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : shbai
 * @date : 2025/2/23 17:21
 * @description :
 */
public interface OrderRepository extends JpaRepository<Order, String> {
}
