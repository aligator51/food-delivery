package com.rapitor3.fastdelivery.restaurantservice.dto.response;

import com.rapitor3.fastdelivery.restaurantservice.model.RestaurantStatus;

import java.time.LocalDateTime;

public record RestaurantDTO (
        Long id,
        String name,
        String description,
        String address,
        String phone,
        Long ownerId,
        RestaurantStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
