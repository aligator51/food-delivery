package com.rapitor3.fastdelivery.orderservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record CreateOrderRequest(
        @NotNull
        @Positive
        Long userId,
        @NotNull
        @Positive
        Long restaurantId,
        @NotBlank
        String deliveryAddress,
        String comment,
        @NotEmpty List<@Valid CreateOrderItemRequest> items

) {
}
