package com.ecommerce.app.ordering.mapper;

import com.ecommerce.app.ordering.domain.Order;
import com.ecommerce.app.ordering.dto.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderResponseMapper {

    @Autowired
    private OrderItemResponseMapper orderItemResponseMapper;

    public OrderResponseDto toDto(Order domain) {
        if (domain == null) return null;

        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(domain.getId() != null ? domain.getId().getValue() : null);
        dto.setUserId(domain.getUserId() != null ? domain.getUserId().getValue() : null);
        dto.setTotalAmount(domain.getTotalAmount());
        dto.setOrderDate(domain.getOrderDate());
        dto.setStatus(domain.getStatus() != null ? domain.getStatus().name() : null);
        dto.setItems(domain.getItems() != null ? 
                domain.getItems().stream()
                        .map(orderItemResponseMapper::toDto)
                        .collect(Collectors.toList()) : null);

        return dto;
    }
}
