package com.ecommerce.ordering.exception;

import com.ecommerce.ordering.exception.ResourceNotFoundException;

import java.util.UUID;

public class CartNotFoundException extends ResourceNotFoundException {
    public CartNotFoundException(UUID userId) {
        super("Cart for user ID " + userId + " not found");
    }
}
