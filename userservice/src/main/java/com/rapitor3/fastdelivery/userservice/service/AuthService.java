package com.rapitor3.fastdelivery.userservice.service;


import com.rapitor3.fastdelivery.userservice.dto.AppUserDTO;
import com.rapitor3.fastdelivery.userservice.dto.AuthResponse;
import com.rapitor3.fastdelivery.userservice.dto.AuthUserRequest;
import com.rapitor3.fastdelivery.userservice.dto.CreateUserRequest;
import com.rapitor3.fastdelivery.userservice.model.AppUser;
import com.rapitor3.fastdelivery.userservice.model.UserRole;
import com.rapitor3.fastdelivery.userservice.model.UserStatus;
import com.rapitor3.fastdelivery.userservice.repository.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;

    public AuthService(AppUserRepository appUserRepository, JwtService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }



    public AppUserDTO register(CreateUserRequest request){

        if (appUserRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        AppUser appUser = AppUser.builder()
                .fullName(request.fullName())
                .email(request.email())
                .passwordHash(request.password())
                .role(UserRole.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .build();
        AppUser savedUser = appUserRepository.save(appUser);
        return toDTO(savedUser);
    }

    public AuthResponse auth(AuthUserRequest request){

        AppUser appUser = appUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Wrong email or password"));

        if (!request.password().equals(appUser.getPasswordHash())) {
            throw new IllegalArgumentException("Wrong email or password");
        }

        String token = jwtService.generateToken(appUser);

        return new AuthResponse(token, "Bearer");

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
