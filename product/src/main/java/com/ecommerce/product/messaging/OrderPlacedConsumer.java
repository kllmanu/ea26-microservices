package com.ecommerce.product.messaging;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.product.domain.ProductId;
import com.ecommerce.product.event.OrderPlacedEvent;
import com.ecommerce.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class OrderPlacedConsumer {

    @Autowired
    private ProductService productService;

    @Bean
    public Consumer<OrderPlacedEvent> reduceStock() {
        return event -> {
            log.info("Received OrderPlacedEvent for order: {}", event.getOrderId());
            event.getItems().forEach(item -> {
                log.info("Reducing stock for product: {} by quantity: {}", item.getProductId(), item.getQuantity());
                try {
                    productService.reduceStock(ProductId.of(item.getProductId()), item.getQuantity());
                } catch (Exception e) {
                    log.error("Failed to reduce stock for product: {}. Error: {}", item.getProductId(), e.getMessage());
                }
            });
        };
    }
}
