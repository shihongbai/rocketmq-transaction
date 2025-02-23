package com.shbai.tx.mqtransaction.service;

import com.shbai.tx.mqtransaction.model.Order;
import com.shbai.tx.mqtransaction.repository.OrderRepository;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : shbai
 * @date : 2025/2/23 16:43
 * @description :
 */
@Service
public class OrderService {
    @Autowired
    private TransactionMQProducer producer;

    @Autowired
    private OrderRepository orderRepository;

    // 创建订单并发送事务消息
    public void createOrder(Order order) throws Exception {
        // 发送半消息，开始事务
        Message message = new Message("OrderTopic", "OrderCreated", order.toString().getBytes());
        producer.sendMessageInTransaction(message, order);

        createOrderInDatabase(order);
    }

    // 本地事务：将订单保存到数据库
    public void createOrderInDatabase(Order order) {
        order.setStatus("CREATED");
        orderRepository.save(order);
    }

    // 查询订单是否创建成功
    public boolean isOrderCreated(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        return order != null && "CREATED".equals(order.getStatus());
    }
}
