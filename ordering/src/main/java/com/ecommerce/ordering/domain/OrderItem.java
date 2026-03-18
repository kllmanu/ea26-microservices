package com.ecommerce.ordering.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class OrderItem {
    private final OrderItemId id;
    private final ProductId productId;
    private final String productName;
    private final BigDecimal unitPrice;
    private final int quantity;

    public BigDecimal getTotalPrice() {
        if (unitPrice == null) {
            return BigDecimal.ZERO;
        }
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
