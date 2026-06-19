package com.rapitor3.fastdelivery.orderservice.model;

public enum OrderStatus {
    CREATED,
    PAID,
    ACCEPTED_BY_RESTAURANT,
    COOKING,
    READY_FOR_DELIVERY,
    DELIVERING,
    DELIVERED,
    CANCELLED,
    FAILED
}
