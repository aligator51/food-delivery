package com.rapitor3.fastdelivery.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthUserRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password

) {
}
