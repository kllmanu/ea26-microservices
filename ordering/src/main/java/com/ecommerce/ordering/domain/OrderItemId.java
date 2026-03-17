package com.ecommerce.app.ordering.domain;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class OrderItemId implements Serializable {
    UUID value;

    public static OrderItemId of(UUID value) {
        return new OrderItemId(value);
    }

    public static OrderItemId generate() {
        return new OrderItemId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
