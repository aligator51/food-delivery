package com.rapitor3.fastdelivery.userservice.dto;

import com.rapitor3.fastdelivery.userservice.model.UserRole;
import com.rapitor3.fastdelivery.userservice.model.UserStatus;

public record AppUserDTO(
        Long id,
        String fullName,
        String email,
        UserRole role,
        UserStatus status
) {
}
