package com.ecommerce.app.ordering.repository;

import com.ecommerce.app.ordering.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;

    @Autowired
    public OrderRepositoryImpl(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Override
    public List<OrderEntity> findByUserId(UUID userId) {
        return jpaOrderRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteByUserId(UUID userId) {
        jpaOrderRepository.deleteByUserId(userId);
    }

    @Override
    public Optional<OrderEntity> findById(UUID id) {
        return jpaOrderRepository.findById(id);
    }

    @Override
    public List<OrderEntity> findAll() {
        return jpaOrderRepository.findAll();
    }

    @Override
    public OrderEntity save(OrderEntity order) {
        return jpaOrderRepository.save(order);
    }

    @Override
    public void deleteById(UUID id) {
        jpaOrderRepository.deleteById(id);
    }
}
