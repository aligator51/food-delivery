package com.rapitor3.fastdelivery.restaurantservice.dto.request;

public record UpdateMenuCategoryRequest(
        String name,
        Integer sortOrder
) {
}
