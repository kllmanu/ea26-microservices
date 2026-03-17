package com.ecommerce.app.ordering.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Shopping cart details")
public class CartResponseDto {
    private UUID id;
    private UUID userId;
    private List<CartItemResponseDto> items;
    private BigDecimal totalAmount;
}
