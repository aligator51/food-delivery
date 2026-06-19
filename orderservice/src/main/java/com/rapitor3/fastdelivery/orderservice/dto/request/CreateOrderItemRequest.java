package com.rapitor3.fastdelivery.orderservice.dto.request;

import java.math.BigDecimal;

public record CreateOrderItemRequest (
        Long menuItemId,
        String menuItemName,
        Integer quantity,
        BigDecimal price
){
}
