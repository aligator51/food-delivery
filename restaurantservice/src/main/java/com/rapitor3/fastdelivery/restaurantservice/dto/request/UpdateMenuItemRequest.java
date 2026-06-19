package com.rapitor3.fastdelivery.restaurantservice.dto.request;

import com.rapitor3.fastdelivery.restaurantservice.model.MenuItemType;

import java.math.BigDecimal;
import java.util.List;

public record UpdateMenuItemRequest(
        Long categoryId,
        String name,
        String description,
        BigDecimal price,
        Boolean available,
        List<String> mediaURLs,
        MenuItemType type
) {
}
