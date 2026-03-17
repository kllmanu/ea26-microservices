package com.ecommerce.user.repository;

import com.ecommerce.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(UUID id);
    List<UserEntity> findAll();
    UserEntity save(UserEntity user);
    boolean existsById(UUID id);
    void deleteById(UUID id);
    long count();
}
