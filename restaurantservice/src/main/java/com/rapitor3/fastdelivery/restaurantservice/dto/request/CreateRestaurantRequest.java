package com.rapitor3.fastdelivery.restaurantservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateRestaurantRequest (
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotBlank
        String address,
        @NotBlank
        String phone,
        @NotNull
        @Positive
        Long ownerId
){
}
