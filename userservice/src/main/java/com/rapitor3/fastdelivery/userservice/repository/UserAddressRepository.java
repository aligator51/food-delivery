package com.rapitor3.fastdelivery.userservice.repository;

import com.rapitor3.fastdelivery.userservice.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
