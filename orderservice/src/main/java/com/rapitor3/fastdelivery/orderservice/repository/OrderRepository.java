package com.rapitor3.fastdelivery.orderservice.repository;

import com.rapitor3.fastdelivery.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByRestaurantId(Long restaurantId);
}
