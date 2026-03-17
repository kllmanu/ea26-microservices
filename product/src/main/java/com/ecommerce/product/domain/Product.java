package com.ecommerce.app.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class Product {
    private final ProductId id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer stock;
    private final Boolean available;

    public boolean isAvailable() {
        return available != null && available;
    }

    public boolean isInStock(int requestedQuantity) {
        return stock != null && stock >= requestedQuantity;
    }
}
