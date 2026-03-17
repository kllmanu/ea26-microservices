package com.ecommerce.app.product.service;

import com.ecommerce.app.product.domain.ProductId;
import com.ecommerce.app.product.dto.ProductCreateRequestDto;
import com.ecommerce.app.product.dto.ProductResponseDto;
import com.ecommerce.app.product.dto.ProductUpdateRequestDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<ProductResponseDto> getAllProducts();
    Optional<ProductResponseDto> getProductById(UUID id);
    ProductResponseDto createProduct(ProductCreateRequestDto productDto);
    Optional<ProductResponseDto> updateProduct(UUID id, ProductUpdateRequestDto productDto);
    boolean deleteProduct(UUID id);
    void checkAvailability(ProductId id);
    void checkStock(ProductId id, Integer requestedQuantity);
    void reduceStock(ProductId id, Integer quantity);
}
