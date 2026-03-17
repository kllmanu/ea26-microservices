package com.ecommerce.app.ordering.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Order details")
public class OrderResponseDto {
    private UUID id;
    private UUID userId;
    private List<OrderItemResponseDto> items;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private String status;
}
