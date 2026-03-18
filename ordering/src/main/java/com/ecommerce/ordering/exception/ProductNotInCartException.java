package com.ecommerce.ordering.exception;

import com.ecommerce.ordering.exception.ResourceNotFoundException;

import java.util.UUID;

public class ProductNotInCartException extends ResourceNotFoundException {
    public ProductNotInCartException(UUID productId) {
        super("Product with ID " + productId + " is not in the cart");
    }
}
