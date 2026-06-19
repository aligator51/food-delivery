package com.rapitor3.fastdelivery.restaurantservice.service;

import com.rapitor3.fastdelivery.restaurantservice.dto.request.CreateMenuCategoryRequest;
import com.rapitor3.fastdelivery.restaurantservice.dto.request.UpdateMenuCategoryRequest;
import com.rapitor3.fastdelivery.restaurantservice.dto.response.MenuCategoryDTO;
import com.rapitor3.fastdelivery.restaurantservice.model.MenuCategory;
import com.rapitor3.fastdelivery.restaurantservice.repository.MenuCategoryRepository;
import com.rapitor3.fastdelivery.restaurantservice.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MenuCategoryService {


    private final MenuCategoryRepository menuCategoryRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuCategoryService(MenuCategoryRepository menuCategoryRepository, RestaurantRepository restaurantRepository) {
        this.menuCategoryRepository = menuCategoryRepository;
        this.restaurantRepository = restaurantRepository;
    }


    /* POST | CREATE
     *
     * Create a menu category
     *
     */
    public MenuCategoryDTO createMenuCategory(Long restaurantId, CreateMenuCategoryRequest request) {


        if (!restaurantRepository.existsById(restaurantId)) {
            throw new IllegalArgumentException();
        }

        MenuCategory menuCategory = MenuCategory.builder()
                .restaurantId(restaurantId)
                .name(request.name())
                .sortOrder(request.sortOrder())
                .active(true)
                .build();

        MenuCategory saved = menuCategoryRepository.save(menuCategory);
        return toDTO(saved);

    }

    /* GET | READ
     *
     * Get all menu categories for this restaurant
     *
     */
    public List<MenuCategoryDTO> getAll(Long restaurantId) {
        return menuCategoryRepository.findAllByRestaurantId(restaurantId).stream().map(this::toDTO).toList();
    }

    /* GET | READ
     *
     * Get a menu category by id
     *
     */
    public MenuCategoryDTO getById(Long restaurantId, Long menuCategoryId) {
        MenuCategory menuCategory = menuCategoryRepository.findById(menuCategoryId).orElseThrow(() -> new IllegalArgumentException());
        if (!menuCategory.getRestaurantId().equals(restaurantId)) {
            throw new IllegalArgumentException();
        }
        return toDTO(menuCategory);
    }

    /* PATCH | UPDATE
     *
     * Change menu category status
     *
     */
    public MenuCategoryDTO changeActive(Long restaurantId, Long menuCategoryId, Boolean status) {
        MenuCategory menuCategory = menuCategoryRepository.findById(menuCategoryId).orElseThrow(() -> new IllegalArgumentException());

        if (status == null) {
            throw new IllegalArgumentException();
        }
        if (!menuCategory.getRestaurantId().equals(restaurantId)) {
            throw new IllegalArgumentException();
        }
        menuCategory.setActive(status);
        MenuCategory saved = menuCategoryRepository.save(menuCategory);
        return toDTO(saved);
    }

    /* PATCH | UPDATE
     *
     * Update menu category info
     *
     */
    public MenuCategoryDTO updateMenuCategory(Long restaurantId, Long menuCategoryId, UpdateMenuCategoryRequest request) {

        MenuCategory menuCategoryToUpdate = menuCategoryRepository.findById(menuCategoryId).orElseThrow(() -> new IllegalArgumentException());
        if (!menuCategoryToUpdate.getRestaurantId().equals(restaurantId)) {
            throw new IllegalArgumentException();
        }
        if (request.name() != null && !request.name().isBlank()) {
            menuCategoryToUpdate.setName(request.name());
        }

        if (request.sortOrder() != null && request.sortOrder() >= 0) {
            menuCategoryToUpdate.setSortOrder(request.sortOrder());
        }

        MenuCategory saved = menuCategoryRepository.save(menuCategoryToUpdate);
        return toDTO(saved);
    }


    private MenuCategoryDTO toDTO(MenuCategory menuCategory) {
        return new MenuCategoryDTO(

                menuCategory.getId(),
                menuCategory.getRestaurantId(),
                menuCategory.getName(),
                menuCategory.getSortOrder(),
                menuCategory.getActive()
        );
    }
}
