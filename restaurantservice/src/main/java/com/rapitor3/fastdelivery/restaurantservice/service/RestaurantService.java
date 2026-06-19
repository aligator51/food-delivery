package com.rapitor3.fastdelivery.restaurantservice.service;

import com.rapitor3.fastdelivery.restaurantservice.dto.request.CreateRestaurantRequest;
import com.rapitor3.fastdelivery.restaurantservice.dto.response.RestaurantDTO;
import com.rapitor3.fastdelivery.restaurantservice.model.Restaurant;
import com.rapitor3.fastdelivery.restaurantservice.model.RestaurantStatus;
import com.rapitor3.fastdelivery.restaurantservice.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }


    public RestaurantDTO createRestaurant(CreateRestaurantRequest request) {

        Restaurant restaurant = Restaurant.builder()
                .name(request.name())
                .description(request.description())
                .address(request.address())
                .status(RestaurantStatus.PENDING)
                .ownerId(request.ownerId())
                .phone(request.phone())
                .build();

        Restaurant saved = restaurantRepository.save(restaurant);
        return toDTO(saved);

    }

    private RestaurantDTO toDTO(Restaurant restaurant) {
        return new RestaurantDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getAddress(),
                restaurant.getPhone(),
                restaurant.getOwnerId(),
                restaurant.getStatus(),
                restaurant.getCreatedAt(),
                restaurant.getUpdatedAt()
        );
    }

    public List<RestaurantDTO> getAll() {
        return restaurantRepository.findAll().stream().map(this::toDTO).toList();
    }

    public RestaurantDTO getById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        return toDTO(restaurant);
    }

    public RestaurantDTO changeStatus(Long id, RestaurantStatus status) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        restaurant.setStatus(status);
        Restaurant saved = restaurantRepository.save(restaurant);
        return toDTO(saved);
    }
}
