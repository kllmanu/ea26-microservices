package com.ecommerce.ordering.service;

import com.ecommerce.ordering.dto.OrderResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponseDto placeOrder(UUID userId);
    List<OrderResponseDto> getOrderHistory(UUID userId);
    List<OrderResponseDto> getAllOrders();
    OrderResponseDto getOrderById(UUID orderId);
}
