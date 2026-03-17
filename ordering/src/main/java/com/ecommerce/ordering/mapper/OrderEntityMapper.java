package com.ecommerce.app.ordering.mapper;

import com.ecommerce.app.ordering.domain.Order;
import com.ecommerce.app.ordering.domain.OrderId;
import com.ecommerce.app.ordering.entity.OrderEntity;
import com.ecommerce.app.user.domain.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderEntityMapper {

    @Autowired
    private OrderItemEntityMapper orderItemEntityMapper;

    public Order toDomain(OrderEntity entity) {
        if (entity == null) return null;

        return Order.builder()
                .id(OrderId.of(entity.getId()))
                .userId(entity.getUser() != null ? UserId.of(entity.getUser().getId()) : null)
                .items(entity.getItems() != null ? 
                        entity.getItems().stream()
                                .map(orderItemEntityMapper::toDomain)
                                .collect(Collectors.toList()) : null)
                .totalAmount(entity.getTotalAmount())
                .orderDate(entity.getOrderDate())
                .status(entity.getStatus())
                .build();
    }
}
