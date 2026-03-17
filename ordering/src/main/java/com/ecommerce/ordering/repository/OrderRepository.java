package com.ecommerce.app.ordering.repository;

import com.ecommerce.app.ordering.entity.OrderEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    List<OrderEntity> findByUserId(UUID userId);
    void deleteByUserId(UUID userId);
    Optional<OrderEntity> findById(UUID id);
    List<OrderEntity> findAll();
    OrderEntity save(OrderEntity order);
    void deleteById(UUID id);
}
