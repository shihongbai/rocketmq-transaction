package com.shbai.tx.mqtransaction.config;

import com.shbai.tx.mqtransaction.service.TransactionListenerImpl;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQConfig {

    @Bean
    public TransactionMQProducer transactionMQProducer(TransactionListenerImpl transactionListener) {
        TransactionMQProducer producer = new TransactionMQProducer("order-service");
        producer.setNamesrvAddr("localhost:9876");
        producer.setTransactionListener(transactionListener);
        return producer;
    }
}
