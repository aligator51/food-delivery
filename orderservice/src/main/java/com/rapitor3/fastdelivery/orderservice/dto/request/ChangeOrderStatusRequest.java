package com.rapitor3.fastdelivery.orderservice.dto.request;

import com.rapitor3.fastdelivery.orderservice.model.OrderStatus;

public record ChangeOrderStatusRequest(

        OrderStatus status
) {
}
