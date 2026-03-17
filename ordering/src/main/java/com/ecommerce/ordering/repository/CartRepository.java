package com.ecommerce.app.ordering.repository;

import com.ecommerce.app.ordering.entity.CartEntity;
import com.ecommerce.app.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository {
    Optional<CartEntity> findByUser(UserEntity user);
    Optional<CartEntity> findByUserId(UUID userId);
    void deleteByUserId(UUID userId);
    Optional<CartEntity> findById(UUID id);
    List<CartEntity> findAll();
    CartEntity save(CartEntity cart);
    void deleteById(UUID id);
}
