package com.rapitor3.fastdelivery.restaurantservice.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChangeMenuItemAvailabilityRequest (
        @NotNull
        Boolean available
){
}
