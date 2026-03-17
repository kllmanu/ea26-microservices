package com.ecommerce.app.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Request object for updating product details")
public class ProductUpdateRequestDto {
    @Schema(description = "Updated name of the product", example = "New Smartphone Model")
    private String name;

    @Schema(description = "Updated description of the product", example = "Updated 5G smartphone with better camera")
    private String description;

    @Schema(description = "Updated price of the product", example = "899.99")
    private BigDecimal price;

    @Schema(description = "Updated stock level", example = "75")
    private Integer stock;

    @Schema(description = "Updated availability status", example = "true")
    private Boolean available;
}
