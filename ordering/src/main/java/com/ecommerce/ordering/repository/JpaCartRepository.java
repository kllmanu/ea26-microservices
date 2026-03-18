package com.ecommerce.ordering.repository;

import com.ecommerce.ordering.entity.CartEntity;
import com.ecommerce.app.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaCartRepository extends JpaRepository<CartEntity, UUID> {
    Optional<CartEntity> findByUser(UserEntity user);
    Optional<CartEntity> findByUserId(UUID userId);
    
    @Modifying
    @Query("DELETE FROM CartEntity c WHERE c.user.id = ?1")
    void deleteByUserId(UUID userId);
}
