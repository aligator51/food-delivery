package com.rapitor3.fastdelivery.userservice.controller;

import com.rapitor3.fastdelivery.userservice.dto.AppUserDTO;
import com.rapitor3.fastdelivery.userservice.dto.CreateUserRequest;
import com.rapitor3.fastdelivery.userservice.model.UserStatus;
import com.rapitor3.fastdelivery.userservice.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @PostMapping("/create")
    public ResponseEntity<AppUserDTO> createAppUser(@RequestBody @Valid CreateUserRequest request

    ) {
        try {
            AppUserDTO response = appUserService.createAppUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppUserDTO>> getAllAppUsers() {
        List<AppUserDTO> appUserList = appUserService.getAll();
        return ResponseEntity.ok(appUserList);

    }


    @GetMapping("/{id}")
    public ResponseEntity<AppUserDTO> getAppUserById(@PathVariable Long id) {
        AppUserDTO response = appUserService.getById(id);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<AppUserDTO> deleteAppUser(@PathVariable Long id) {
        try {

            AppUserDTO response = appUserService.changeStatus(id, UserStatus.DELETED);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }


    }


}
