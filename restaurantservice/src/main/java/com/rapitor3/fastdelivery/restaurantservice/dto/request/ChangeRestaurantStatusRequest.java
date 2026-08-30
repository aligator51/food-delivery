package com.rapitor3.fastdelivery.restaurantservice.dto.request;

import com.rapitor3.fastdelivery.restaurantservice.model.RestaurantStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeRestaurantStatusRequest (
        @NotNull
        RestaurantStatus status
) {
}
