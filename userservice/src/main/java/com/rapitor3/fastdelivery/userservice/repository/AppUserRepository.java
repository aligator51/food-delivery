package com.rapitor3.fastdelivery.userservice.repository;

import com.rapitor3.fastdelivery.userservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    boolean existsByEmail(String email);
}
