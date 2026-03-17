package com.ecommerce.app.ordering.exception;

import com.ecommerce.app.common.exception.BadRequestException;
import com.ecommerce.app.ordering.entity.OrderEntity.OrderStatus;

public class InvalidOrderStatusException extends BadRequestException {
    public InvalidOrderStatusException(OrderStatus current, OrderStatus target) {
        super("Cannot change order status from " + current + " to " + target);
    }
}
