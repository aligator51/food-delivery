package com.rapitor3.fastdelivery.restaurantservice.dto.response;

import com.rapitor3.fastdelivery.restaurantservice.model.MenuItemType;

import java.math.BigDecimal;
import java.util.List;

public record MenuItemDTO (
        Long id,
        Long restaurantId,
        Long categoryId,
        MenuItemType type,
        String name,
        String description,
        BigDecimal price,
        Boolean available,
        List<String> imageURLs
){
}