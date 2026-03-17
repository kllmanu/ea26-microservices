package com.ecommerce.app.ordering.repository;

import com.ecommerce.app.ordering.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByUserId(UUID userId);
    
    @Modifying
    @Query("DELETE FROM OrderEntity o WHERE o.user.id = ?1")
    void deleteByUserId(UUID userId);
}
