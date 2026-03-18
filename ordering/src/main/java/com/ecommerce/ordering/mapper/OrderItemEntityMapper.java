package com.ecommerce.ordering.mapper;

import com.ecommerce.ordering.domain.OrderItem;
import com.ecommerce.ordering.domain.OrderItemId;
import com.ecommerce.ordering.entity.OrderItemEntity;
import com.ecommerce.app.product.domain.ProductId;
import org.springframework.stereotype.Component;

@Component
public class OrderItemEntityMapper {

    public OrderItem toDomain(OrderItemEntity entity) {
        if (entity == null) return null;

        return new OrderItem(
                OrderItemId.of(entity.getId()),
                ProductId.of(entity.getProductId()),
                entity.getProductName(),
                entity.getUnitPrice(),
                entity.getQuantity()
        );
    }

    public OrderItemEntity toEntity(OrderItem domain) {
        if (domain == null) return null;

        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(domain.getId() != null ? domain.getId().getValue() : null);
        entity.setProductId(domain.getProductId() != null ? domain.getProductId().getValue() : null);
        entity.setProductName(domain.getProductName());
        entity.setUnitPrice(domain.getUnitPrice());
        entity.setQuantity(domain.getQuantity());
        entity.setTotalPrice(domain.getTotalPrice());
        return entity;
    }
}
