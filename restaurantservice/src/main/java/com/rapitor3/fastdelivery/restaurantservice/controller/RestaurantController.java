package com.rapitor3.fastdelivery.restaurantservice.controller;

import com.rapitor3.fastdelivery.restaurantservice.dto.request.ChangeRestaurantStatusRequest;
import com.rapitor3.fastdelivery.restaurantservice.dto.request.CreateRestaurantRequest;
import com.rapitor3.fastdelivery.restaurantservice.dto.request.UpdateRestaurantRequest;
import com.rapitor3.fastdelivery.restaurantservice.dto.response.RestaurantDTO;
import com.rapitor3.fastdelivery.restaurantservice.model.RestaurantStatus;
import com.rapitor3.fastdelivery.restaurantservice.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;


    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @PostMapping("/create")
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody @Valid CreateRestaurantRequest request

    ) {
        try {
            RestaurantDTO response = restaurantService.createRestaurant(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<RestaurantDTO> restaurantList = restaurantService.getAll();
        return ResponseEntity.ok(restaurantList);

    }


    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long id) {
        RestaurantDTO response = restaurantService.getById(id);
        return ResponseEntity.ok(response);

    }

    @PatchMapping("/{id}/change")
    public ResponseEntity<RestaurantDTO> changeRestaurantStatus(
            @PathVariable Long id,
            @RequestBody @Valid ChangeRestaurantStatusRequest request
    ) {
        try {

            RestaurantDTO response = restaurantService.changeStatus(id, request.status());
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }


    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantDTO> updateRestaurant(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRestaurantRequest request
    ) {
        try {
            RestaurantDTO response = restaurantService.updateRestaurant(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
