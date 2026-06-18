package com.rapitor3.fastdelivery.userservice.controller;


import com.rapitor3.fastdelivery.userservice.dto.AppUserDTO;
import com.rapitor3.fastdelivery.userservice.dto.AuthResponse;
import com.rapitor3.fastdelivery.userservice.dto.AuthUserRequest;
import com.rapitor3.fastdelivery.userservice.dto.CreateUserRequest;
import com.rapitor3.fastdelivery.userservice.service.AppUserService;
import com.rapitor3.fastdelivery.userservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AppUserDTO> registerAppUser(@RequestBody @Valid CreateUserRequest request

    ) {
        try {
            AppUserDTO response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authAppUser(@RequestBody @Valid AuthUserRequest request

    ) {
        try {
            AuthResponse response = authService.auth(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }



}
