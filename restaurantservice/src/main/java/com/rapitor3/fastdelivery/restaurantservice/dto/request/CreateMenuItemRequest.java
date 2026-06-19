package com.rapitor3.fastdelivery.restaurantservice.dto.request;

import java.math.BigDecimal;
import java.util.List;

public record CreateMenuItemRequest (
        Long categoryId,
        Long restaurantId,
        String name,
        String description,
        BigDecimal price,
        Boolean available,
        List<String> imageURLs
){
}
