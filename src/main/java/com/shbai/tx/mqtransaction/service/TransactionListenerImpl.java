package com.shbai.tx.mqtransaction.service;

import com.shbai.tx.mqtransaction.model.Order;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : shbai
 * @date : 2025/2/23 17:19
 * @description :
 */
@Service
public class TransactionListenerImpl implements TransactionListener {
    private static final Logger logger = LoggerFactory.getLogger(TransactionListenerImpl.class);

    @Autowired
    private OrderService orderService;


    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        // 执行本地事务
        try {
            orderService.createOrder((Order) o);
            // 提交半消息
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            logger.error("executeLocalTransaction error", e);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        // 本地消息反查
        String orderId = new String(messageExt.getBody());
        if (orderService.isOrderCreated(orderId)) {
            return LocalTransactionState.COMMIT_MESSAGE;
        } else {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }
}
