package com.ecommerce.ordering.mapper;

import com.ecommerce.ordering.domain.Order;
import com.ecommerce.ordering.domain.OrderId;
import com.ecommerce.ordering.domain.UserId;
import com.ecommerce.ordering.entity.OrderEntity;
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
                .userId(entity.getUserId() != null ? UserId.of(entity.getUserId()) : null)
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
