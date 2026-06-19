package com.rapitor3.fastdelivery.restaurantservice.service;

import com.rapitor3.fastdelivery.restaurantservice.dto.request.CreateRestaurantRequest;
import com.rapitor3.fastdelivery.restaurantservice.dto.request.UpdateRestaurantRequest;
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


    /* POST | CREATE
     *
     * Create a restaurant
     *
     */
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

    /* GET | READ
     *
     * Get all restaurants
     *
     */
    public List<RestaurantDTO> getAll() {
        return restaurantRepository.findAll().stream().map(this::toDTO).toList();
    }

    /* GET | READ
     *
     * Get a restaurant by id
     *
     */
    public RestaurantDTO getById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        return toDTO(restaurant);
    }

    /* PATCH | UPDATE
     *
     * Change restaurant status
     *
     */
    public RestaurantDTO changeStatus(Long id, RestaurantStatus status) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        restaurant.setStatus(status);
        Restaurant saved = restaurantRepository.save(restaurant);
        return toDTO(saved);
    }

    /* PATCH | UPDATE
     *
     * Update restaurant info
     *
     */
    public RestaurantDTO updateRestaurant(Long id, UpdateRestaurantRequest request) {

        Restaurant restaurantToUpdate = restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());

        if (request.name() != null && !request.name().isBlank()) {
            restaurantToUpdate.setName(request.name());
        }

        if (request.description() != null) {
            restaurantToUpdate.setDescription(request.description());
        }

        if (request.address() != null && !request.address().isBlank()) {
            restaurantToUpdate.setAddress(request.address());
        }

        if (request.phone() != null && !request.phone().isBlank()) {
            restaurantToUpdate.setPhone(request.phone());
        }

        Restaurant saved = restaurantRepository.save(restaurantToUpdate);
        return toDTO(saved);
    }
}
