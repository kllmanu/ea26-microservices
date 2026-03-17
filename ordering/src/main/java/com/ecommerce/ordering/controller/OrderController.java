package com.ecommerce.app.ordering.controller;

import com.ecommerce.app.ordering.dto.OrderResponseDto;
import com.ecommerce.app.ordering.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Management", description = "Endpoints for managing orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/user/{userId}")
    @Operation(summary = "Place an order", description = "Creates a new order from the user's current shopping cart")
    public ResponseEntity<OrderResponseDto> placeOrder(@PathVariable UUID userId) {
        return ResponseEntity.ok(orderService.placeOrder(userId));
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Returns a list of all orders in the system")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's order history", description = "Returns all previous orders for a given user")
    public ResponseEntity<List<OrderResponseDto>> getOrderHistory(@PathVariable UUID userId) {
        return ResponseEntity.ok(orderService.getOrderHistory(userId));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID", description = "Returns details of a specific order")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}
