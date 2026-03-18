package com.ecommerce.ordering.service;

import com.ecommerce.ordering.domain.Cart;
import com.ecommerce.ordering.dto.AddToCartRequestDto;
import com.ecommerce.ordering.dto.CartResponseDto;
import com.ecommerce.ordering.entity.CartEntity;
import com.ecommerce.ordering.entity.CartItemEntity;
import com.ecommerce.ordering.exception.CartNotFoundException;
import com.ecommerce.ordering.exception.ProductNotInCartException;
import com.ecommerce.ordering.mapper.CartEntityMapper;
import com.ecommerce.ordering.mapper.CartItemEntityMapper;
import com.ecommerce.ordering.mapper.CartResponseMapper;
import com.ecommerce.ordering.repository.CartRepository;
import com.ecommerce.app.product.domain.ProductId;
import com.ecommerce.app.product.domain.Product;
import com.ecommerce.app.product.entity.ProductEntity;
import com.ecommerce.app.product.exception.ProductNotFoundException;
import com.ecommerce.app.product.mapper.ProductEntityMapper;
import com.ecommerce.app.product.repository.ProductRepository;
import com.ecommerce.app.product.service.ProductService;
import com.ecommerce.app.user.entity.UserEntity;
import com.ecommerce.app.user.exception.UserNotFoundException;
import com.ecommerce.app.user.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartEntityMapper cartEntityMapper;

    @Autowired
    private CartItemEntityMapper cartItemEntityMapper;

    @Autowired
    private ProductEntityMapper productEntityMapper;

    @Autowired
    private CartResponseMapper cartResponseMapper;

    @Transactional
    public CartResponseDto getCartByUserId(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
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
        ProductId productId = ProductId.of(request.getProductId());
        
        productService.checkAvailability(productId);
        productService.checkStock(productId, request.getQuantity());

        CartEntity cartEntity = getOrCreateCart(userId);
        Cart cartDomain = cartEntityMapper.toDomain(cartEntity);

        ProductEntity productEntity = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));

        cartDomain.addOrUpdateItem(
                ProductId.of(productEntity.getId()),
                productEntity.getName(),
                productEntity.getPrice(),
                request.getQuantity()
        );

        // Sync domain back to entity
        updateEntityFromDomain(cartEntity, cartDomain);
        
        CartEntity savedCart = cartRepository.save(cartEntity);
        return cartResponseMapper.toDto(cartEntityMapper.toDomain(savedCart));
    }

    @Transactional
    public CartResponseDto removeItemFromCart(UUID userId, UUID productId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        
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
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        
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
                    UserEntity user = userRepository.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException(userId));
                    CartEntity newCart = CartEntity.builder()
                            .user(user)
                            .build();
                    return cartRepository.save(newCart);
                });
    }
}
