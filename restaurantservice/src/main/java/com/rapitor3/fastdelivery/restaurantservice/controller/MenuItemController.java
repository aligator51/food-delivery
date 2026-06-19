package com.rapitor3.fastdelivery.restaurantservice.controller;


import com.rapitor3.fastdelivery.restaurantservice.dto.request.ChangeMenuItemAvailabilityRequest;
import com.rapitor3.fastdelivery.restaurantservice.dto.request.CreateMenuItemRequest;
import com.rapitor3.fastdelivery.restaurantservice.dto.request.UpdateMenuItemRequest;
import com.rapitor3.fastdelivery.restaurantservice.dto.response.MenuItemDTO;
import com.rapitor3.fastdelivery.restaurantservice.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menu")
public class MenuItemController {


    private final MenuItemService menuItemService;


    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }


    @PostMapping("/create")
    public ResponseEntity<MenuItemDTO> createMenuItem(
            @PathVariable Long restaurantId,
            @RequestBody @Valid CreateMenuItemRequest request

    ) {
        try {
            MenuItemDTO response = menuItemService.createMenuItem(restaurantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuItemDTO>> getRestaurantAllMenuItems(
            @PathVariable Long restaurantId
    ){
        List<MenuItemDTO> list = menuItemService.getAllByRestaurantId(restaurantId);
        return ResponseEntity.ok(list);

    }


    @GetMapping("/available")
    public ResponseEntity<List<MenuItemDTO>> getAvailableMenuItems(
            @PathVariable Long restaurantId
    ){

        List<MenuItemDTO> list = menuItemService.getAvailableByRestaurantId(restaurantId);
        return ResponseEntity.ok(list);

    }


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<MenuItemDTO>> getMenuItemsByCategory(
            @PathVariable Long restaurantId,
            @PathVariable Long categoryId
    ){
        List<MenuItemDTO> list = menuItemService.getByCategoryId(restaurantId, categoryId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{menuItemId}")
    public ResponseEntity<MenuItemDTO> getMenuItemById(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId
    ){
        MenuItemDTO item = menuItemService.getById(restaurantId, menuItemId);
        return ResponseEntity.ok(item);
    }


    @PatchMapping("/{menuItemId}")
    public ResponseEntity<MenuItemDTO> updateMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId,
            @RequestBody @Valid UpdateMenuItemRequest request
    ){
        try {
            MenuItemDTO response = menuItemService.updateMenuItem(restaurantId, menuItemId, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{menuItemId}/availability")
    public ResponseEntity<MenuItemDTO> changeMenuItemAvailability(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId,
            @RequestBody @Valid ChangeMenuItemAvailabilityRequest request
    ){
        try {
            MenuItemDTO response = menuItemService.changeAvailability(restaurantId, menuItemId, request.available());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
