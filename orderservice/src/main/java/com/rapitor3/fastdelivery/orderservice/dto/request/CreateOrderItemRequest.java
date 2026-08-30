package com.rapitor3.fastdelivery.orderservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateOrderItemRequest (
        @NotNull
        @Positive
        Long menuItemId,
        @NotBlank
        String menuItemName,
        @NotNull
        @Positive
        Integer quantity,
        @NotNull
        @Positive
        BigDecimal price
){
}
