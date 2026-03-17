package com.ecommerce.app.ordering.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Request to add a product to the cart")
public class AddToCartRequestDto {
    @Schema(description = "Product ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID productId;
    
    @Schema(description = "Quantity", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;
}
