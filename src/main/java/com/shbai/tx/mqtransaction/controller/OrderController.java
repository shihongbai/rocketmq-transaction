package com.shbai.tx.mqtransaction.controller;

import com.shbai.tx.mqtransaction.model.Order;
import com.shbai.tx.mqtransaction.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : shbai
 * @date : 2025/2/23 17:21
 * @description :
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public String createOrder(@RequestBody Order order) throws Exception {
        orderService.createOrder(order);
        return "Order created and transaction started!";
    }
}
