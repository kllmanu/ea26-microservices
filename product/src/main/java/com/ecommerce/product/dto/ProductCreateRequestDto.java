package com.ecommerce.app.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Request object for creating a new product")
public class ProductCreateRequestDto {
    @Schema(description = "The name of the product", example = "Smartphone", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "The description of the product", example = "Latest 5G smartphone")
    private String description;

    @Schema(description = "The price of the product", example = "999.99", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    @Schema(description = "The initial stock level", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer stock;

    @Schema(description = "Initial availability status", example = "true")
    private Boolean available;
}
