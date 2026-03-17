package com.ecommerce.app.ordering.mapper;

import com.ecommerce.app.ordering.domain.OrderItem;
import com.ecommerce.app.ordering.dto.OrderItemResponseDto;
import org.springframework.stereotype.Component;

@Component
public class OrderItemResponseMapper {

    public OrderItemResponseDto toDto(OrderItem domain) {
        if (domain == null) return null;

        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setId(domain.getId() != null ? domain.getId().getValue() : null);
        dto.setQuantity(domain.getQuantity());
        dto.setTotalPrice(domain.getTotalPrice());
        
        dto.setProductId(domain.getProductId() != null ? domain.getProductId().getValue() : null);
        dto.setProductName(domain.getProductName());
        dto.setUnitPrice(domain.getUnitPrice());

        return dto;
    }
}
