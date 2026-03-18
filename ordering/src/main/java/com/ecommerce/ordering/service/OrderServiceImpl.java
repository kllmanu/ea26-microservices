package com.ecommerce.ordering.service;

import com.ecommerce.ordering.client.ProductClient;
import com.ecommerce.ordering.client.UserClient;
import com.ecommerce.ordering.domain.Order;
import com.ecommerce.ordering.domain.OrderItem;
import com.ecommerce.ordering.domain.ProductId;
import com.ecommerce.ordering.domain.UserId;
import com.ecommerce.ordering.dto.OrderResponseDto;
import com.ecommerce.ordering.dto.ProductDTO;
import com.ecommerce.ordering.dto.UserDTO;
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
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderEntityMapper orderEntityMapper;

    @Autowired
    private OrderResponseMapper orderResponseMapper;

    @Autowired
    private OrderItemEntityMapper orderItemEntityMapper;

    @Transactional
    public OrderResponseDto placeOrder(UUID userId) {
        // Validate user via Feign
        UserDTO user = userClient.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found: " + userId);
        }

        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException(userId));

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException(userId);
        }

        // Check availability and stock for all items via Feign
        cart.getItems().forEach(item -> {
            ProductDTO product = productClient.getProductById(item.getProductId());
            if (product == null || !product.getAvailable()) {
                throw new RuntimeException("Product not available: " + item.getProductId());
            }
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + item.getProductId());
            }
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
                .userId(userId)
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
        // Optional: validate user exists via Feign if needed
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
