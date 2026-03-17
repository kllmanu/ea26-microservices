package com.ecommerce.app.ordering.exception;

import com.ecommerce.app.common.exception.ResourceNotFoundException;

import java.util.UUID;

public class ProductNotInCartException extends ResourceNotFoundException {
    public ProductNotInCartException(UUID productId) {
        super("Product with ID " + productId + " is not in the cart");
    }
}
