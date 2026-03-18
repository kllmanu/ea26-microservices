package com.ecommerce.ordering.domain;

import lombok.Value;
import java.io.Serializable;
import java.util.UUID;

@Value
public class UserId implements Serializable {
    UUID value;

    public static UserId of(UUID value) {
        return new UserId(value);
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
