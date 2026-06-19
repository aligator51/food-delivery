package com.rapitor3.fastdelivery.restaurantservice.repository;

import com.rapitor3.fastdelivery.restaurantservice.model.MenuItem;
import com.rapitor3.fastdelivery.restaurantservice.model.MenuItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findAllByRestaurantId(Long restaurantId);

    List<MenuItem> findAllByRestaurantIdAndAvailableTrue(Long restaurantId);

    List<MenuItem> findAllByRestaurantIdAndCategoryId(Long restaurantId, Long categoryId);

    List<MenuItem> findAllByType(MenuItemType type);

    List<MenuItem> findAllByTypeAndAvailableTrue(MenuItemType type);


}
