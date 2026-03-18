package com.ecommerce.product.repository;

import com.ecommerce.product.entity.ProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Optional<ProductEntity> findById(UUID id);
    List<ProductEntity> findAll();
    ProductEntity save(ProductEntity product);
    boolean existsById(UUID id);
    void deleteById(UUID id);
    long count();
}
