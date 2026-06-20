package com.rapitor3.fastdelivery.orderservice.event;

import com.rapitor3.fastdelivery.orderservice.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        Long restaurantId,
        OrderStatus status,
        BigDecimal totalPrice,
        LocalDateTime createdAt
) {
}