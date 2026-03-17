package com.ecommerce.app.ordering.domain;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class CartId implements Serializable {
    UUID value;

    public static CartId of(UUID value) {
        return new CartId(value);
    }

    public static CartId generate() {
        return new CartId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
