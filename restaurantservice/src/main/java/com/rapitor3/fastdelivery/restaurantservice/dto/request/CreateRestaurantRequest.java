package com.rapitor3.fastdelivery.restaurantservice.dto.request;

public record CreateRestaurantRequest (
        String name,
        String description,
        String address,
        String phone,
        Long ownerId
){
}
