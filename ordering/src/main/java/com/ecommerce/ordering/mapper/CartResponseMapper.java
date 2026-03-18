package com.ecommerce.ordering.mapper;

import com.ecommerce.ordering.domain.Cart;
import com.ecommerce.ordering.dto.CartResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartResponseMapper {

    @Autowired
    private CartItemResponseMapper cartItemResponseMapper;

    public CartResponseDto toDto(Cart domain) {
        if (domain == null) return null;

        CartResponseDto dto = new CartResponseDto();
        dto.setId(domain.getId() != null ? domain.getId().getValue() : null);
        dto.setUserId(domain.getUserId() != null ? domain.getUserId().getValue() : null);
        dto.setTotalAmount(domain.getTotalAmount());
        
        if (domain.getItems() != null) {
            dto.setItems(domain.getItems().stream()
                    .map(cartItemResponseMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
