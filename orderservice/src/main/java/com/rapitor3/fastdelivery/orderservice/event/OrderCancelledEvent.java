package com.rapitor3.fastdelivery.orderservice.event;

import java.time.LocalDateTime;

public record OrderCancelledEvent(

        Long orderId,
        Long userId,
        Long restaurantId,
        String reason,
        LocalDateTime cancelledAt


) {
}
