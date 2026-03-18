package com.ecommerce.product.repository;

import com.ecommerce.product.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

    @Autowired
    public ProductRepositoryImpl(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public Optional<ProductEntity> findById(UUID id) {
        return jpaProductRepository.findById(id);
    }

    @Override
    public List<ProductEntity> findAll() {
        return jpaProductRepository.findAll();
    }

    @Override
    public ProductEntity save(ProductEntity product) {
        return jpaProductRepository.save(product);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaProductRepository.existsById(id);
    }

    @Override
    public void deleteById(UUID id) {
        jpaProductRepository.deleteById(id);
    }

    @Override
    public long count() {
        return jpaProductRepository.count();
    }
}
