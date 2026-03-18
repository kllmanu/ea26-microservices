package com.ecommerce.product.domain;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class ProductId implements Serializable {
    UUID value;

    public static ProductId of(UUID value) {
        return new ProductId(value);
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
