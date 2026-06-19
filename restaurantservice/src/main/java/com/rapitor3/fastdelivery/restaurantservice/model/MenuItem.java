package com.rapitor3.fastdelivery.restaurantservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_items_seq")
    @SequenceGenerator(
            name = "menu_items_seq",
            sequenceName = "menu_items_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal  price;

    @Column(nullable = false)
    private BigDecimal available;

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "menu_item_media",
            joinColumns = @JoinColumn(name = "menu_item_id")
    )
    @Column(name = "media_url")
    private List<String> mediaURLs = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
