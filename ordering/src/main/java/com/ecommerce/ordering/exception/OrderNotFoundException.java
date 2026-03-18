package com.ecommerce.ordering.exception;

import com.ecommerce.ordering.exception.ResourceNotFoundException;

import java.util.UUID;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(UUID orderId) {
        super("Order not found with id: " + orderId);
    }
}
