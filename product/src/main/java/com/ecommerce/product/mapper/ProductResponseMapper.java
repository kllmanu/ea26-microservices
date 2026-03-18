package com.ecommerce.product.mapper;

import com.ecommerce.product.domain.Product;
import com.ecommerce.product.dto.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseMapper {

    public ProductResponseDto toDto(Product domain) {
        if (domain == null) return null;

        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(domain.getId() != null ? domain.getId().getValue() : null);
        dto.setName(domain.getName());
        dto.setDescription(domain.getDescription());
        dto.setPrice(domain.getPrice());
        dto.setStock(domain.getStock());
        dto.setAvailable(domain.getAvailable());
        return dto;
    }
}
