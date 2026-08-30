package com.rapitor3.fastdelivery.restaurantservice.dto.request;

import com.rapitor3.fastdelivery.restaurantservice.model.MenuItemType;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record UpdateMenuItemRequest(
        @Positive
        Long categoryId,
        String name,
        String description,
        @Positive
        BigDecimal price,
        Boolean available,
        List<String> mediaURLs,
        MenuItemType type
) {
}
