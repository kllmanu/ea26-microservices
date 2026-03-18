package com.ecommerce.product.mapper;

import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductId;
import com.ecommerce.product.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityMapper {

    public Product toDomain(ProductEntity entity) {
        if (entity == null) return null;

        return Product.builder()
                .id(ProductId.of(entity.getId()))
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .available(entity.getAvailable())
                .build();
    }

    public ProductEntity toEntity(Product domain) {
        if (domain == null) return null;

        return ProductEntity.builder()
                .id(domain.getId() != null ? domain.getId().getValue() : null)
                .name(domain.getName())
                .description(domain.getDescription())
                .price(domain.getPrice())
                .stock(domain.getStock())
                .available(domain.getAvailable())
                .build();
    }

    public void updateEntityFromDomain(ProductEntity entity, Product domain) {
        if (domain == null) return;

        if (domain.getName() != null) entity.setName(domain.getName());
        if (domain.getDescription() != null) entity.setDescription(domain.getDescription());
        if (domain.getPrice() != null) entity.setPrice(domain.getPrice());
        if (domain.getStock() != null) entity.setStock(domain.getStock());
        if (domain.getAvailable() != null) entity.setAvailable(domain.getAvailable());
    }
}
