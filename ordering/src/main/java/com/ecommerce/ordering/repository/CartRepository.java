package com.ecommerce.ordering.repository;

import com.ecommerce.ordering.entity.CartEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository {
    Optional<CartEntity> findByUserId(UUID userId);
    void deleteByUserId(UUID userId);
    Optional<CartEntity> findById(UUID id);
    List<CartEntity> findAll();
    CartEntity save(CartEntity cart);
    void deleteById(UUID id);
}
