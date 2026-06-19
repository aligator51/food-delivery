package com.rapitor3.fastdelivery.restaurantservice.dto.request;

public record UpdateRestaurantRequest(
        String name,
        String description,
        String address,
        String phone
){
}
