package com.rapitor3.fastdelivery.restaurantservice.service;


import com.rapitor3.fastdelivery.restaurantservice.dto.request.CreateMenuItemRequest;
import com.rapitor3.fastdelivery.restaurantservice.dto.request.UpdateMenuItemRequest;
import com.rapitor3.fastdelivery.restaurantservice.dto.response.MenuItemDTO;
import com.rapitor3.fastdelivery.restaurantservice.model.MenuItem;
import com.rapitor3.fastdelivery.restaurantservice.model.MenuItemType;
import com.rapitor3.fastdelivery.restaurantservice.repository.MenuCategoryRepository;
import com.rapitor3.fastdelivery.restaurantservice.repository.MenuItemRepository;
import com.rapitor3.fastdelivery.restaurantservice.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuItemService {


    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    public MenuItemService(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository, MenuCategoryRepository menuCategoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuCategoryRepository = menuCategoryRepository;
    }

    @Transactional
    public MenuItemDTO createMenuItem(Long restaurantId, CreateMenuItemRequest request) {

        if (!restaurantRepository.existsById(restaurantId)) {
            throw new IllegalArgumentException();
        }
        if (!menuCategoryRepository.existsById(request.categoryId())) {
            throw new IllegalArgumentException();
        }
        MenuItem menuItem = MenuItem.builder()
                .restaurantId(restaurantId)
                .categoryId(request.categoryId())
                .type(request.type())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .available(request.available())
                .mediaURLs(request.mediaURLs())
                .build();

        MenuItem saved = menuItemRepository.save(menuItem);
        return toDTO(saved);
    }

    public List<MenuItemDTO> getAllByRestaurantId(Long restaurantId) {
        return menuItemRepository.findAllByRestaurantId(restaurantId).stream().map(this::toDTO).toList();
    }

    public List<MenuItemDTO> getAvailableByRestaurantId(Long restaurantId) {
        return menuItemRepository.findAllByRestaurantIdAndAvailableTrue(restaurantId).stream().map(this::toDTO).toList();

    }

    public List<MenuItemDTO> getByCategoryId(Long restaurantId, Long categoryId) {
        return menuItemRepository.findAllByRestaurantIdAndCategoryId(restaurantId, categoryId).stream().map(this::toDTO).toList();
    }

    public MenuItemDTO getById(Long restaurantId, Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new IllegalArgumentException());
        if (!menuItem.getRestaurantId().equals(restaurantId)) {
            throw new IllegalArgumentException();
        }

        return toDTO(menuItem);
    }


    @Transactional
    public MenuItemDTO updateMenuItem(Long restaurantId, Long menuItemId, UpdateMenuItemRequest request) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new IllegalArgumentException());
        if (!menuItem.getRestaurantId().equals(restaurantId)) {
            throw new IllegalArgumentException();
        }

        if (request.categoryId() != null) menuItem.setCategoryId(request.categoryId());
        if (request.name() != null) menuItem.setName(request.name());
        if (request.description() != null) menuItem.setDescription(request.description());
        if (request.price() != null) menuItem.setPrice(request.price());
        if (request.available() != null) menuItem.setAvailable(request.available());
        if (request.mediaURLs() != null) menuItem.setMediaURLs(request.mediaURLs());
        if (request.type() != null) menuItem.setType(request.type());

        MenuItem saved = menuItemRepository.save(menuItem);
        return toDTO(saved);
    }

    @Transactional
    public MenuItemDTO changeAvailability(Long restaurantId, Long menuItemId, Boolean available) {

        if (available == null) {
            throw new IllegalArgumentException();
        }

        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new IllegalArgumentException());
        if (!menuItem.getRestaurantId().equals(restaurantId)) {
            throw new IllegalArgumentException();
        }

        menuItem.setAvailable(available);

        MenuItem saved = menuItemRepository.save(menuItem);
        return toDTO(saved);
    }

    public List<MenuItemDTO> getByType(MenuItemType type) {
        return menuItemRepository.findAllByType(type).stream().map(this::toDTO).toList();
    }

    public List<MenuItemDTO> getAvailableByType(MenuItemType type) {
        return menuItemRepository.findAllByTypeAndAvailableTrue(type).stream().map(this::toDTO).toList();
    }

    public MenuItemDTO toDTO(MenuItem menuItem) {
        return new MenuItemDTO(

                menuItem.getId(),
                menuItem.getRestaurantId(),
                menuItem.getCategoryId(),
                menuItem.getType(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailable(),
                menuItem.getMediaURLs()
        );
    }
}
