package com.ecommerce.ordering.mapper;

import com.ecommerce.ordering.domain.Cart;
import com.ecommerce.ordering.domain.CartId;
import com.ecommerce.ordering.domain.UserId;
import com.ecommerce.ordering.entity.CartEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartEntityMapper {

    @Autowired
    private CartItemEntityMapper cartItemEntityMapper;

    public Cart toDomain(CartEntity entity) {
        if (entity == null) return null;

        return Cart.builder()
                .id(CartId.of(entity.getId()))
                .userId(entity.getUserId() != null ? UserId.of(entity.getUserId()) : null)
                .items(entity.getItems() != null ? 
                        entity.getItems().stream()
                                .map(cartItemEntityMapper::toDomain)
                                .collect(Collectors.toList()) : null)
                .build();
    }
}
