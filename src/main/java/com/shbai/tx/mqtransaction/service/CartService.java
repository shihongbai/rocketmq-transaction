package com.shbai.tx.mqtransaction.service;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author : shbai
 * @date : 2025/2/23 17:02
 * @description :
 */
@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public void startConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("cart-service");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("OrderTopic", "OrderCreated");

        consumer.registerMessageListener((MessageListenerConcurrently) (msgList, context) -> {
            for (MessageExt message : msgList) {
                System.out.println("接收到订单消息：" + new String(message.getBody()));
                // 删除购物车商品的逻辑
            }
            return null;
        });

        consumer.start();
    }
}
