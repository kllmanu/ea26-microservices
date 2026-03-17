package com.ecommerce.app.ordering.service;

import com.ecommerce.app.ordering.dto.OrderResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponseDto placeOrder(UUID userId);
    List<OrderResponseDto> getOrderHistory(UUID userId);
    List<OrderResponseDto> getAllOrders();
    OrderResponseDto getOrderById(UUID orderId);
}
