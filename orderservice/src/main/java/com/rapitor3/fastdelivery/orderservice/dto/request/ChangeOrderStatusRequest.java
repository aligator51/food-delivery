package com.rapitor3.fastdelivery.orderservice.dto.request;

import com.rapitor3.fastdelivery.orderservice.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeOrderStatusRequest(
        @NotNull
        OrderStatus status
) {
}
