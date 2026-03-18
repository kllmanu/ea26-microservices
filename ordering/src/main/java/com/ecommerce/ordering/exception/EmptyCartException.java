package com.ecommerce.ordering.exception;

import com.ecommerce.ordering.exception.BadRequestException;

import java.util.UUID;

public class EmptyCartException extends BadRequestException {
    public EmptyCartException(UUID userId) {
        super("Cannot place order for user " + userId + " with an empty cart");
    }
}
