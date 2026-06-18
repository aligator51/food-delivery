package com.rapitor3.fastdelivery.userservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "user_address_seq",
            sequenceName = "user_address_seq",
            allocationSize = 1
    )
    private Long id;

    private String city;

    private String street;

    private String house;

    private String appartament;

    private String floor;

    private String comment;

    private boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;
}
