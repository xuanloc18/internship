package com.cxl.identity_service.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cxl.identity_service.entity.User;

@Repository
public interface UserRespository extends JpaRepository<User, String> {
    Optional<User> findByUserName(String userName);

    boolean existsByuserName(String username);
}
