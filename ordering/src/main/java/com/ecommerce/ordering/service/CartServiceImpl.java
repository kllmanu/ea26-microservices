package com.ecommerce.ordering.service;

import com.ecommerce.ordering.client.ProductClient;
import com.ecommerce.ordering.client.UserClient;
import com.ecommerce.ordering.domain.Cart;
import com.ecommerce.ordering.domain.ProductId;
import com.ecommerce.ordering.dto.AddToCartRequestDto;
import com.ecommerce.ordering.dto.CartResponseDto;
import com.ecommerce.ordering.dto.ProductDTO;
import com.ecommerce.ordering.dto.UserDTO;
import com.ecommerce.ordering.entity.CartEntity;
import com.ecommerce.ordering.entity.CartItemEntity;
import com.ecommerce.ordering.exception.CartNotFoundException;
import com.ecommerce.ordering.exception.ProductNotInCartException;
import com.ecommerce.ordering.mapper.CartEntityMapper;
import com.ecommerce.ordering.mapper.CartItemEntityMapper;
import com.ecommerce.ordering.mapper.CartResponseMapper;
import com.ecommerce.ordering.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartEntityMapper cartEntityMapper;

    @Autowired
    private CartItemEntityMapper cartItemEntityMapper;

    @Autowired
    private CartResponseMapper cartResponseMapper;

    @Transactional
    public CartResponseDto getCartByUserId(UUID userId) {
        // Validate user via Feign
        UserDTO user = userClient.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found: " + userId);
        }
        CartEntity cartEntity = getOrCreateCart(userId);
        return cartResponseMapper.toDto(cartEntityMapper.toDomain(cartEntity));
    }

    public List<CartResponseDto> getAllCarts() {
        return cartRepository.findAll().stream()
                .map(cartEntityMapper::toDomain)
                .map(cartResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartResponseDto addItemToCart(UUID userId, AddToCartRequestDto request) {
        // Fetch product info via Feign
        ProductDTO product = productClient.getProductById(request.getProductId());
        if (product == null || !product.getAvailable()) {
            throw new RuntimeException("Product not available: " + request.getProductId());
        }
        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + request.getProductId());
        }

        CartEntity cartEntity = getOrCreateCart(userId);
        Cart cartDomain = cartEntityMapper.toDomain(cartEntity);

        cartDomain.addOrUpdateItem(
                ProductId.of(product.getId()),
                product.getName(),
                product.getPrice(),
                request.getQuantity()
        );

        // Sync domain back to entity
        updateEntityFromDomain(cartEntity, cartDomain);
        
        CartEntity savedCart = cartRepository.save(cartEntity);
        return cartResponseMapper.toDto(cartEntityMapper.toDomain(savedCart));
    }

    @Transactional
    public CartResponseDto removeItemFromCart(UUID userId, UUID productId) {
        // Validate user via Feign if needed
        
        CartEntity cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException(userId));
        
        boolean itemExists = cartEntity.getItems().stream()
                .anyMatch(item -> item.getProductId().equals(productId));
        
        if (!itemExists) {
            throw new ProductNotInCartException(productId);
        }

        Cart cartDomain = cartEntityMapper.toDomain(cartEntity);
        cartDomain.removeItem(ProductId.of(productId));

        updateEntityFromDomain(cartEntity, cartDomain);

        CartEntity savedCart = cartRepository.save(cartEntity);
        return cartResponseMapper.toDto(cartEntityMapper.toDomain(savedCart));
    }

    @Transactional
    public void clearCart(UUID userId) {
        CartEntity cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException(userId));
        
        cartEntity.getItems().clear();
        cartRepository.save(cartEntity);
    }

    private void updateEntityFromDomain(CartEntity entity, Cart domain) {
        // Remove items no longer in domain
        entity.getItems().removeIf(itemEntity -> 
            domain.getItems().stream().noneMatch(domainItem -> 
                domainItem.getProductId().getValue().equals(itemEntity.getProductId())));

        // Update existing or add new
        for (var domainItem : domain.getItems()) {
            var existingEntity = entity.getItems().stream()
                    .filter(itemEntity -> itemEntity.getProductId().equals(domainItem.getProductId().getValue()))
                    .findFirst();

            if (existingEntity.isPresent()) {
                existingEntity.get().setQuantity(domainItem.getQuantity());
                existingEntity.get().setUnitPrice(domainItem.getUnitPrice());
                existingEntity.get().setProductName(domainItem.getProductName());
            } else {
                CartItemEntity newEntity = cartItemEntityMapper.toEntity(domainItem);
                newEntity.setCart(entity);
                entity.getItems().add(newEntity);
            }
        }
    }

    private CartEntity getOrCreateCart(UUID userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    // Validate user existence via Feign before creating cart
                    UserDTO user = userClient.getUserById(userId);
                    if (user == null) {
                        throw new RuntimeException("User not found: " + userId);
                    }
                    CartEntity newCart = CartEntity.builder()
                            .userId(userId)
                            .build();
                    return cartRepository.save(newCart);
                });
    }
}
