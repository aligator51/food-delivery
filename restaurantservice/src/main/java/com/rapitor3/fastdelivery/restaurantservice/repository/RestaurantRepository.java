package com.rapitor3.fastdelivery.restaurantservice.repository;

import com.rapitor3.fastdelivery.restaurantservice.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
