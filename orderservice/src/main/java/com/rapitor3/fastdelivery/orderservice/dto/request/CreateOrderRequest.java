package com.rapitor3.fastdelivery.orderservice.dto.request;

import java.util.List;

public record CreateOrderRequest(
        Long userId,
        Long restaurantId,
        String deliveryAddress,
        String comment,
        List<CreateOrderItemRequest> items

) {
}
