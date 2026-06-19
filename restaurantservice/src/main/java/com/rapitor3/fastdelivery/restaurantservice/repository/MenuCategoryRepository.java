package com.rapitor3.fastdelivery.restaurantservice.repository;

import com.rapitor3.fastdelivery.restaurantservice.model.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
}
