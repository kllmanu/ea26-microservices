package com.ecommerce.ordering.exception;

import com.ecommerce.ordering.exception.BadRequestException;
import com.ecommerce.ordering.entity.OrderEntity.OrderStatus;

public class InvalidOrderStatusException extends BadRequestException {
    public InvalidOrderStatusException(OrderStatus current, OrderStatus target) {
        super("Cannot change order status from " + current + " to " + target);
    }
}
