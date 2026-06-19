package com.rapitor3.fastdelivery.orderservice.dto.response;

import java.math.BigDecimal;

public record OrderItemDTO(

        Long id,
        Long menuItemId,
        String  menuItemName,
        Integer quantity,
        BigDecimal price,
        BigDecimal totalPrice

) {
}
