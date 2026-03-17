package com.ecommerce.app.ordering.controller;

import com.ecommerce.app.ordering.dto.AddToCartRequestDto;
import com.ecommerce.app.ordering.dto.CartResponseDto;
import com.ecommerce.app.ordering.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
@Tag(name = "Cart Management", description = "Endpoints for managing shopping carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    @Operation(summary = "Get all carts", description = "Returns a list of all shopping carts in the system")
    public ResponseEntity<List<CartResponseDto>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's cart", description = "Returns the current shopping cart for a given user")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable UUID userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/user/{userId}/items")
    @Operation(summary = "Add item to cart", description = "Adds a product to the user's shopping cart")
    public ResponseEntity<CartResponseDto> addItem(@PathVariable UUID userId, @RequestBody AddToCartRequestDto request) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, request));
    }

    @DeleteMapping("/user/{userId}/items/{productId}")
    @Operation(summary = "Remove item from cart", description = "Removes a product from the user's shopping cart")
    public ResponseEntity<CartResponseDto> removeItem(@PathVariable UUID userId, @PathVariable UUID productId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(userId, productId));
    }

    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Clear cart", description = "Removes all items from the user's shopping cart")
    public ResponseEntity<Void> clearCart(@PathVariable UUID userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
