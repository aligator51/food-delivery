package com.rapitor3.fastdelivery.restaurantservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateMenuCategoryRequest (

        @NotBlank
        String name,

        @NotNull
        @PositiveOrZero
        Integer sortOrder
){
}
