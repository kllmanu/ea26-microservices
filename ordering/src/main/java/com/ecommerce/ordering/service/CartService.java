package com.ecommerce.app.ordering.service;

import com.ecommerce.app.ordering.dto.AddToCartRequestDto;
import com.ecommerce.app.ordering.dto.CartResponseDto;

import java.util.List;
import java.util.UUID;

public interface CartService {
    CartResponseDto getCartByUserId(UUID userId);
    List<CartResponseDto> getAllCarts();
    CartResponseDto addItemToCart(UUID userId, AddToCartRequestDto request);
    CartResponseDto removeItemFromCart(UUID userId, UUID productId);
    void clearCart(UUID userId);
}
