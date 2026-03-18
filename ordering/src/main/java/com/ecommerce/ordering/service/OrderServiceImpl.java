package com.ecommerce.ordering.service;

import com.ecommerce.ordering.domain.Order;
import com.ecommerce.ordering.domain.OrderItem;
import com.ecommerce.ordering.dto.OrderResponseDto;
import com.ecommerce.ordering.entity.CartEntity;
import com.ecommerce.ordering.entity.OrderEntity;
import com.ecommerce.ordering.entity.OrderItemEntity;
import com.ecommerce.ordering.exception.CartNotFoundException;
import com.ecommerce.ordering.exception.EmptyCartException;
import com.ecommerce.ordering.exception.OrderNotFoundException;
import com.ecommerce.ordering.mapper.OrderEntityMapper;
import com.ecommerce.ordering.mapper.OrderItemEntityMapper;
import com.ecommerce.ordering.mapper.OrderResponseMapper;
import com.ecommerce.ordering.repository.CartRepository;
import com.ecommerce.ordering.repository.OrderRepository;
import com.ecommerce.app.product.domain.ProductId;
import com.ecommerce.app.product.service.ProductService;
import com.ecommerce.app.user.domain.UserId;
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
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderEntityMapper orderEntityMapper;

    @Autowired
    private OrderResponseMapper orderResponseMapper;

    @Autowired
    private OrderItemEntityMapper orderItemEntityMapper;

    @Transactional
    public OrderResponseDto placeOrder(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException(userId));

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException(userId);
        }

        // Check availability and stock for all items
        cart.getItems().forEach(item -> {
            ProductId productId = ProductId.of(item.getProductId());
            productService.checkAvailability(productId);
            productService.checkStock(productId, item.getQuantity());
        });

        // Reduce stock for all items
        cart.getItems().forEach(item -> {
            productService.reduceStock(ProductId.of(item.getProductId()), item.getQuantity());
        });

        // Create Order items from Cart items
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(item -> new OrderItem(
                        null,
                        ProductId.of(item.getProductId()),
                        item.getProductName(),
                        item.getUnitPrice(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());

        // Create and Save Order
        Order orderDomain = Order.create(UserId.of(userId), orderItems);
        OrderEntity orderEntity = OrderEntity.builder()
                .user(user)
                .totalAmount(orderDomain.getTotalAmount())
                .orderDate(orderDomain.getOrderDate())
                .status(orderDomain.getStatus())
                .build();

        List<OrderItemEntity> itemEntities = orderItems.stream()
                .map(item -> {
                    OrderItemEntity itemEntity = orderItemEntityMapper.toEntity(item);
                    itemEntity.setOrder(orderEntity);
                    return itemEntity;
                })
                .collect(Collectors.toList());
        
        orderEntity.setItems(itemEntities);
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        // Clear Cart
        cart.getItems().clear();
        cartRepository.save(cart);

        return orderResponseMapper.toDto(orderEntityMapper.toDomain(savedOrder));
    }

    public List<OrderResponseDto> getOrderHistory(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        return orderRepository.findByUserId(userId).stream()
                .map(orderEntityMapper::toDomain)
                .map(orderResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderEntityMapper::toDomain)
                .map(orderResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderResponseDto getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .map(orderEntityMapper::toDomain)
                .map(orderResponseMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
