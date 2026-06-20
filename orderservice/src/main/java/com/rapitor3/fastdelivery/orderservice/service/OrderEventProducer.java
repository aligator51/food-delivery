package com.rapitor3.fastdelivery.orderservice.service;


import com.rapitor3.fastdelivery.orderservice.event.KafkaTopics;
import com.rapitor3.fastdelivery.orderservice.event.OrderCancelledEvent;
import com.rapitor3.fastdelivery.orderservice.event.OrderCreatedEvent;
import com.rapitor3.fastdelivery.orderservice.event.OrderStatusChangedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        kafkaTemplate.send(KafkaTopics.ORDER_CREATED, event.orderId().toString(), event);
    }

    public void sendOrderStatusChangedEvent(OrderStatusChangedEvent event) {
        kafkaTemplate.send(KafkaTopics.ORDER_STATUS_CHANGED, event.orderId().toString(), event);
    }

    public void sendOrderCancelledEvent(OrderCancelledEvent event) {
        kafkaTemplate.send(KafkaTopics.ORDER_CANCELLED, event.orderId().toString(), event);
    }
}
