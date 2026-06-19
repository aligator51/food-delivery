package com.rapitor3.fastdelivery.restaurantservice.dto.request;

import com.rapitor3.fastdelivery.restaurantservice.model.RestaurantStatus;

public record ChangeRestaurantStatusRequest (
        RestaurantStatus status
) {
}
