package com.rapitor3.fastdelivery.orderservice.event;

import com.rapitor3.fastdelivery.orderservice.model.OrderStatus;

import java.time.LocalDateTime;

public record OrderStatusChangedEvent(
        Long orderId,
        Long userId,
        Long restaurantId,
        OrderStatus oldStatus,
        OrderStatus newStatus,
        LocalDateTime changedAt
) {
}