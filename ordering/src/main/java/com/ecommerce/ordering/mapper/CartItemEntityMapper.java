package com.ecommerce.ordering.mapper;

import com.ecommerce.ordering.domain.CartItem;
import com.ecommerce.ordering.domain.CartItemId;
import com.ecommerce.ordering.domain.ProductId;
import com.ecommerce.ordering.entity.CartItemEntity;
import org.springframework.stereotype.Component;

@Component
public class CartItemEntityMapper {

    public CartItem toDomain(CartItemEntity entity) {
        if (entity == null) return null;

        return new CartItem(
                CartItemId.of(entity.getId()),
                ProductId.of(entity.getProductId()),
                entity.getProductName(),
                entity.getUnitPrice(),
                entity.getQuantity()
        );
    }

    public CartItemEntity toEntity(CartItem domain) {
        if (domain == null) return null;

        CartItemEntity entity = new CartItemEntity();
        entity.setId(domain.getId() != null ? domain.getId().getValue() : null);
        entity.setProductId(domain.getProductId() != null ? domain.getProductId().getValue() : null);
        entity.setProductName(domain.getProductName());
        entity.setUnitPrice(domain.getUnitPrice());
        entity.setQuantity(domain.getQuantity());
        return entity;
    }
}
