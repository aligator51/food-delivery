package com.rapitor3.fastdelivery.orderservice.dto.response;

import com.rapitor3.fastdelivery.orderservice.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO (
        Long id,
        Long userId,
        Long restaurantId,
        OrderStatus status,
        BigDecimal totalPrice,
        String deliveryAddress,
        String comment,
        List<OrderItemDTO> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt


) {
}
