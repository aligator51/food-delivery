package com.rapitor3.fastdelivery.userservice.service;

import com.rapitor3.fastdelivery.userservice.dto.AppUserDTO;
import com.rapitor3.fastdelivery.userservice.model.AppUser;
import com.rapitor3.fastdelivery.userservice.model.UserRole;
import com.rapitor3.fastdelivery.userservice.model.UserStatus;
import com.rapitor3.fastdelivery.userservice.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUserDTO createAppUser(
            String fullName,
            String email,
            String passwordHash,
            UserRole userRole
    ) {
        if (appUserRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        AppUser appUser = AppUser.builder()
                .fullName(fullName)
                .email(email)
                .passwordHash(passwordHash)
                .role(userRole == null ? UserRole.CUSTOMER : userRole)
                .status(UserStatus.ACTIVE)
                .build();
        AppUser savedUser = appUserRepository.save(appUser);
        return toDTO(savedUser);
    }

    public List<AppUserDTO> getAll() {
        return appUserRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public AppUserDTO changeStatus(Long id, UserStatus status){

        AppUser user = appUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        user.setStatus(status);

        AppUser savedUser = appUserRepository.save(user);

        return toDTO(savedUser);

    }


    public AppUserDTO getById(Long id) {

        AppUser user = appUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        return toDTO(user);
    }


    public AppUserDTO toDTO(AppUser appUser) {
        return new AppUserDTO(
                appUser.getId(),
                appUser.getFullName(),
                appUser.getEmail(),
                appUser.getRole(),
                appUser.getStatus()
        );

    }


}
