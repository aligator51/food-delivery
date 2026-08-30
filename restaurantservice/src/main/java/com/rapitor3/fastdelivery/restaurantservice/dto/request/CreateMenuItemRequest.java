package com.rapitor3.fastdelivery.restaurantservice.dto.request;

import com.rapitor3.fastdelivery.restaurantservice.model.MenuItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record CreateMenuItemRequest (


        Long restaurantId,
        @NotNull
        @Positive
        Long categoryId,
        @NotNull
        MenuItemType type,
        @NotBlank
        String name,

        String description,
        @NotNull
        @Positive
        BigDecimal price,
        @NotNull
        Boolean available,

        List<String> mediaURLs
){
}
