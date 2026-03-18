package com.ecommerce.ordering.domain;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class CartItemId implements Serializable {
    UUID value;

    public static CartItemId of(UUID value) {
        return new CartItemId(value);
    }

    public static CartItemId generate() {
        return new CartItemId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
