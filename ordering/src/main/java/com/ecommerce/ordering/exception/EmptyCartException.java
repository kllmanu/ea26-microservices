package com.ecommerce.app.ordering.exception;

import com.ecommerce.app.common.exception.BadRequestException;

import java.util.UUID;

public class EmptyCartException extends BadRequestException {
    public EmptyCartException(UUID userId) {
        super("Cannot place order for user " + userId + " with an empty cart");
    }
}
