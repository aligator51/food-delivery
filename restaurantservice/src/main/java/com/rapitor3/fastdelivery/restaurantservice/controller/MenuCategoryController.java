package com.rapitor3.fastdelivery.restaurantservice.controller;

import com.rapitor3.fastdelivery.restaurantservice.dto.request.*;
import com.rapitor3.fastdelivery.restaurantservice.dto.response.MenuCategoryDTO;
import com.rapitor3.fastdelivery.restaurantservice.service.MenuCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/categories")
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    public MenuCategoryController(MenuCategoryService menuCategoryService) {
        this.menuCategoryService = menuCategoryService;
    }


    @PostMapping("/create")
    public ResponseEntity<MenuCategoryDTO> createMenuCategory(
            @PathVariable Long restaurantId,
            @RequestBody @Valid CreateMenuCategoryRequest request

    ) {
        try {
            MenuCategoryDTO response = menuCategoryService.createMenuCategory(restaurantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuCategoryDTO>> getAllMenuCategories(
            @PathVariable Long restaurantId
    ) {
        List<MenuCategoryDTO> menuCategoryList = menuCategoryService.getAll(restaurantId);
        return ResponseEntity.ok(menuCategoryList);

    }


    @GetMapping("/{menuCategoryId}")
    public ResponseEntity<MenuCategoryDTO> getMenuCategoryById(
            @PathVariable Long restaurantId,
            @PathVariable Long menuCategoryId
    ) {
        try {
            MenuCategoryDTO response = menuCategoryService.getById(restaurantId, menuCategoryId);
            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PatchMapping("/{menuCategoryId}/active")
    public ResponseEntity<MenuCategoryDTO> changeMenuCategoryStatus(
            @PathVariable Long restaurantId,
            @PathVariable Long menuCategoryId,
            @RequestBody @Valid ChangeMenuCategoryStatusRequest request
    ) {
        try {

            MenuCategoryDTO response = menuCategoryService.changeActive(restaurantId, menuCategoryId, request.active());
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }


    }

    @PatchMapping("/{menuCategoryId}")
    public ResponseEntity<MenuCategoryDTO> updateMenuCategory(
            @PathVariable Long restaurantId,
            @PathVariable Long menuCategoryId,
            @RequestBody @Valid UpdateMenuCategoryRequest request
    ) {
        try {
            MenuCategoryDTO response = menuCategoryService.updateMenuCategory(restaurantId, menuCategoryId, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
