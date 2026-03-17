package com.ecommerce.app.ordering.mapper;

import com.ecommerce.app.ordering.domain.CartItem;
import com.ecommerce.app.ordering.domain.CartItemId;
import com.ecommerce.app.ordering.entity.CartItemEntity;
import com.ecommerce.app.product.domain.ProductId;
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
