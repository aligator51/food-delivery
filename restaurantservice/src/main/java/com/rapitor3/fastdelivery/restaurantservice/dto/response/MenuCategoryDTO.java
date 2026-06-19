package com.rapitor3.fastdelivery.restaurantservice.dto.response;

public record MenuCategoryDTO (
        Long id,
        Long restaurantId,
        String name,
        Integer sortOrder,
        Boolean active
) {
}
