package com.rapitor3.fastdelivery.userservice.dto;

public record AuthResponse(
        String accessToken,
        String tokenType
) {
}
