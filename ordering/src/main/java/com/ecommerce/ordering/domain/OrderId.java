package com.ecommerce.app.ordering.domain;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class OrderId implements Serializable {
    UUID value;

    public static OrderId of(UUID value) {
        return new OrderId(value);
    }

    public static OrderId generate() {
        return new OrderId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
