package com.ecommerce.ordering.domain;

import com.ecommerce.ordering.entity.OrderEntity.OrderStatus;
import com.ecommerce.app.user.domain.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Order {
    private final OrderId id;
    private final UserId userId;
    private final List<OrderItem> items;
    private final BigDecimal totalAmount;
    private final LocalDateTime orderDate;
    private final OrderStatus status;

    public static Order create(UserId userId, List<OrderItem> items) {
        BigDecimal total = items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return Order.builder()
                .id(OrderId.generate())
                .userId(userId)
                .items(items)
                .totalAmount(total)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .build();
    }
}
