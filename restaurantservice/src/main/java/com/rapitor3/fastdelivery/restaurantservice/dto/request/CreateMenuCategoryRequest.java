package com.rapitor3.fastdelivery.restaurantservice.dto.request;

public record CreateMenuCategoryRequest (
        String name,
        Integer sortOrder
){
}
