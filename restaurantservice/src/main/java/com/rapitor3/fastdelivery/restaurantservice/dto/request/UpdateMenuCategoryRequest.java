package com.rapitor3.fastdelivery.restaurantservice.dto.request;

import jakarta.validation.constraints.PositiveOrZero;

public record UpdateMenuCategoryRequest(

        String name,
        @PositiveOrZero
        Integer sortOrder
) {
}
