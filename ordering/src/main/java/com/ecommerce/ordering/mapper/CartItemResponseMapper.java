package com.ecommerce.app.ordering.mapper;

import com.ecommerce.app.ordering.domain.CartItem;
import com.ecommerce.app.ordering.dto.CartItemResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CartItemResponseMapper {

    public CartItemResponseDto toDto(CartItem domain) {
        if (domain == null) return null;

        CartItemResponseDto dto = new CartItemResponseDto();
        dto.setId(domain.getId() != null ? domain.getId().getValue() : null);
        dto.setQuantity(domain.getQuantity());
        dto.setTotalPrice(domain.getTotalPrice());
        
        dto.setProductId(domain.getProductId() != null ? domain.getProductId().getValue() : null);
        dto.setProductName(domain.getProductName());
        dto.setUnitPrice(domain.getUnitPrice());

        return dto;
    }
}
