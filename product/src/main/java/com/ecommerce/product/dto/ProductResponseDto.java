package com.ecommerce.app.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "Response object containing product details")
public class ProductResponseDto {
    @Schema(description = "The unique identifier of the product", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "The name of the product", example = "Laptop")
    private String name;

    @Schema(description = "The description of the product", example = "High-performance gaming laptop")
    private String description;

    @Schema(description = "The price of the product", example = "1200.50")
    private BigDecimal price;

    @Schema(description = "The current stock level", example = "50")
    private Integer stock;

    @Schema(description = "Whether the product is currently available for purchase", example = "true")
    private Boolean available;
}
