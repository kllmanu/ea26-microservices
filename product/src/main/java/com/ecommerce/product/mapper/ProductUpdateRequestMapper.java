package com.ecommerce.app.product.mapper;

import com.ecommerce.app.product.domain.Product;
import com.ecommerce.app.product.dto.ProductUpdateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdateRequestMapper {

    public Product toDomain(ProductUpdateRequestDto dto) {
        if (dto == null) return null;

        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .available(dto.getAvailable())
                .build();
    }
}
