package com.ecommerce.product.exception;

import com.ecommerce.product.exception.ResourceNotFoundException;
import com.ecommerce.product.domain.ProductId;

import java.util.UUID;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(UUID id) {
        super("Product with ID " + id + " not found");
    }

    public ProductNotFoundException(ProductId id) {
        super("Product with ID " + id.getValue() + " not found");
    }
}
