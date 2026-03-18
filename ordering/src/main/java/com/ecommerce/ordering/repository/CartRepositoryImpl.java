package com.ecommerce.ordering.repository;

import com.ecommerce.ordering.entity.CartEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CartRepositoryImpl implements CartRepository {

    private final JpaCartRepository jpaCartRepository;

    @Autowired
    public CartRepositoryImpl(JpaCartRepository jpaCartRepository) {
        this.jpaCartRepository = jpaCartRepository;
    }

    @Override
    public Optional<CartEntity> findByUserId(UUID userId) {
        return jpaCartRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteByUserId(UUID userId) {
        jpaCartRepository.deleteByUserId(userId);
    }

    @Override
    public Optional<CartEntity> findById(UUID id) {
        return jpaCartRepository.findById(id);
    }

    @Override
    public List<CartEntity> findAll() {
        return jpaCartRepository.findAll();
    }

    @Override
    public CartEntity save(CartEntity cart) {
        return jpaCartRepository.save(cart);
    }

    @Override
    public void deleteById(UUID id) {
        jpaCartRepository.deleteById(id);
    }
}
